package de.jworks.fpp.labs.layout;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.fop.hyphenation.Hyphenation;
import org.apache.fop.hyphenation.Hyphenator;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import com.ibm.icu.text.BreakIterator;

import de.jworks.fpp.labs.story.Line;
import de.jworks.fpp.labs.story.Paragraph;
import de.jworks.fpp.labs.story.Run;
import de.jworks.fpp.labs.story.Story;
import de.jworks.fpp.labs.stylesheet.CharacterStyle;
import de.jworks.fpp.labs.stylesheet.Stylesheet;

public class LayoutContext {
	
	private final Stylesheet stylesheet;

	private final PDDocument document;
	
	private final Map<String, PDFont> fonts = new HashMap<String, PDFont>();
	
	private final Map<String, BreakIterator> lineBreakIterators = new HashMap<String, BreakIterator>();

	public LayoutContext(Stylesheet stylesheet) {
		this.stylesheet = stylesheet;
		try {
			document = new PDDocument();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void close() {
		try {
			document.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	// ===== TEXT =====
	
	public Story[] layout(Story story, Long left, Long top, Long right, Long bottom) {
		Story newStory = new Story(story);
		Story rest = null;
		Long _top = top;
		for (Paragraph paragraph : story.paragraphs) {
			if (rest != null) {
				rest.paragraphs.add(paragraph);
			} else {
				Paragraph[] layoutResult = layout(paragraph, left, _top, right, bottom);
				newStory.paragraphs.add(layoutResult[0]);
				if (_top != null) {
					// TODO update _top
				}
				if (layoutResult[1] != null) {
					rest = new Story(story);
					rest.paragraphs.add(layoutResult[1]);
				}
			}
		}
		// TODO update position of newStory
		return new Story[] { newStory, rest };
	}
	
	public Paragraph[] layout(Paragraph paragraph, Long left, Long top, Long right, Long bottom) {
		Paragraph newParagraph = new Paragraph(paragraph);
		Paragraph rest = null;
		Long _top = top;
		for (Line line : paragraph.lines) {
			if (rest != null) {
				rest.lines.add(line);
			} else {
				Line _line = line;
				while (_line != null) {
					Line[] layoutResult = layout(_line, left, _top, right, bottom);
					newParagraph.lines.add(layoutResult[0]);
					if (_top != null) {
						// TODO update _top
					}
					_line = layoutResult[1];
				}
			}
		}
		// TODO update position of newParagraph
		return new Paragraph[] { newParagraph, rest };
	}
	
	public Line[] layout(Line line, Long left, Long top, Long right, Long bottom) {
		Line newLine = new Line(line);
		Line rest = null;
		Long _left = left;
		for (Run run : line.runs) {
			if (rest != null) {
				rest.runs.add(run);
			} else {
				Run[] layoutResult = layout(run, _left, top, right, bottom);
				if (layoutResult[0] != null) {
					newLine.runs.add(layoutResult[0]);
					if (_left != null) {
						CharacterStyle characterStyle = stylesheet.getCharacterStyle(run.characterStyleName);
						PDFont font = getFont(characterStyle.getFontName());
						try {
							_left += (long) (font.getStringWidth(layoutResult[0].content) * characterStyle.getFontSize());
						} catch (IOException e) {
							throw new RuntimeException(e);
						}
					}
				}
				if (layoutResult[1] != null) {
					rest = new Line(line);
					rest.runs.add(layoutResult[1]);
				}
			}
		}
		// TODO update position of newLine
		return new Line[] { newLine, rest };
	}
	
	public Run[] layout(Run run, Long left, Long top, Long right, Long bottom) {
		try {
			long width = (left != null && right != null) ? right - left : Long.MAX_VALUE;
			CharacterStyle characterStyle = stylesheet.getCharacterStyle(run.characterStyleName);
			PDFont font = getFont(characterStyle.getFontName());
			BreakIterator lineBreakIterator = getLineBreakIterator(run.content, run.language);
			for (int end = lineBreakIterator.next(); end != BreakIterator.DONE; end = lineBreakIterator.next()) {
				float stringWidth = font.getStringWidth(run.content.substring(0, end)) * characterStyle.getFontSize();
				if (stringWidth > width) {
					int wordStart = lineBreakIterator.previous();
					int wordEnd = lineBreakIterator.next();
					String word = run.content.substring(wordStart, wordEnd);
					if (word.length() >= 6) {
						Hyphenation hyphenation = Hyphenator.hyphenate(run.language, null, null, null, word, 2, 3);
						if (hyphenation != null) {
							int[] hyphenationPoints = hyphenation.getHyphenationPoints();
							ArrayUtils.reverse(hyphenationPoints);
							for (int hyphenationPoint : hyphenationPoints) {
								stringWidth = font.getStringWidth(run.content.substring(0, wordStart + hyphenationPoint) + "-") * characterStyle.getFontSize();
								if (stringWidth <= width) {
									return new Run[] { 
											new Run(run, run.content.substring(0, wordStart + hyphenationPoint) + "-"),
											new Run(run, run.content.substring(wordStart + hyphenationPoint))
									};
								}
							}
						}
					}
					return new Run[] {
							new Run(run, run.content.substring(0, wordStart)), 
							new Run(run, run.content.substring(wordStart)) };
				}
			}
			return new Run[] { new Run(run, run.content), null };
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private PDFont getFont(String fontName) {
		PDFont font = fonts.get(fontName);
		if (font == null) {
			fonts.put(fontName, font = createFont(fontName));
		}
		return font;
	}
	
	private PDFont createFont(String fontName) {
		PDFont font = null;
		try {
			font = PDTrueTypeFont.loadTTF(document, fontName);
		} catch (Exception e) {
			// TODO log info
			font = PDType1Font.getStandardFont(fontName);
		}
		return font;
	}
	
	private BreakIterator getLineBreakIterator(String text, String language) {
		BreakIterator lineBreakIterator = lineBreakIterators.get(language);
		if (lineBreakIterator == null) {
			lineBreakIterators.put(language, lineBreakIterator = createLineBreakIterator(language));
		}
		lineBreakIterator.setText(text);
		return lineBreakIterator;
	}

	private BreakIterator createLineBreakIterator(String language) {
		return BreakIterator.getLineInstance(new Locale(language));
	}
	
}
