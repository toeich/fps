package de.jworks.fpp.labs.layout;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.fop.hyphenation.Hyphenation;
import org.apache.fop.hyphenation.Hyphenator;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDFontDescriptor;
import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import com.ibm.icu.text.BreakIterator;

import de.jworks.fpp.labs.story.Line;
import de.jworks.fpp.labs.story.Paragraph;
import de.jworks.fpp.labs.story.Run;
import de.jworks.fpp.labs.story.Story;
import de.jworks.fpp.labs.stylesheet.CharacterStyle;
import de.jworks.fpp.labs.stylesheet.ParagraphStyle;
import de.jworks.fpp.labs.stylesheet.Stylesheet;

public class LayoutContext {
	
	private final Stylesheet stylesheet;

	private final PDDocument document;
	
	private final Map<String, PDFont> fonts = new HashMap<String, PDFont>();
	
	private final Map<String, BreakIterator> lineBreakIterators = new HashMap<String, BreakIterator>();
	
	private Integer numHyphens;
	
	private Double lastBaseline;
	
	@SuppressWarnings("serial")
	private final Map<String, Object> cache = new LinkedHashMap<String, Object>() {
		
		private static final long CACHE_SIZE = 1000;
		
		protected boolean removeEldestEntry(Map.Entry<String,Object> eldest) {
			return size() > CACHE_SIZE;
		};
		
	};
	
	private int cacheHits = 0;
	
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
			System.out.println("cacheHits = " + cacheHits);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	// ===== TEXT BOX =====
	
	public TextBox[] layout(TextBox textBox, Double left, Double top, Double right, Double bottom) {
		Double _left = left;
		if (_left != null && textBox.frameColor != null) {
			_left += textBox.frameWidth;
		}
		Double _top = top;
		if (_top != null && textBox.frameColor != null) {
			_top -= textBox.frameWidth;
		}
		Double _right = right;
		if (_right != null && textBox.frameColor != null) {
			_right -= textBox.frameWidth;
		}
		Double _bottom = bottom;
		if (_bottom != null && textBox.frameColor != null) {
			_bottom += textBox.frameWidth;
		}
		TextBox newTextBox = new TextBox();
		TextBox rest = null;
		Story[] layoutResult = layout(textBox.story, _left, _top, _right, _bottom);
		
		return new TextBox[] { null, textBox };
	}
	
	// ===== STORY =====
	
	public Story[] layout(Story story, Double left, Double top, Double right, Double bottom) {
		numHyphens = 0;
		lastBaseline = null;
		Story newStory = new Story(story);
		Story rest = null;
		Double _top = top;
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
	
	public Paragraph[] layout(Paragraph paragraph, Double left, Double top, Double right, Double bottom) {
		ParagraphStyle paragraphStyle = getParagraphStyle(paragraph.paragraphStyleName);
		Paragraph newParagraph = new Paragraph(paragraph);
		Paragraph rest = null;
		Double _top = top;
		for (Line line : paragraph.lines) {
			if (rest != null) {
				rest.lines.add(line);
			} else {
				Line _line = line;
				while (_line != null) {
					Line[] layoutResult = layout(_line, left, _top, right, bottom, paragraphStyle);
					newParagraph.lines.add(layoutResult[0]);
					if (_top != null) {
						double maxFontSize = 0.0;
						double maxAscent = 0.0;
						double maxDescent = 0.0;
						for (Run run : layoutResult[0].runs) {
							CharacterStyle characterStyle = getCharacterStyle(run.getCharacterStyleName(), paragraphStyle);
							maxFontSize = Math.max(maxFontSize, characterStyle.getFontSize());
							maxAscent = Math.max(maxAscent, getAscent(characterStyle));
							maxDescent = Math.min(maxDescent, getDescent(characterStyle));
						}
						double baseline;
						if (lastBaseline == null) {
							baseline = _top + maxAscent;
						} else {
							Double leading = paragraphStyle.getLeading();
							if (leading == null) {
								leading = maxFontSize * 1.2; // 120% of font size
							}
							baseline = lastBaseline + leading;
						}
						layoutResult[0].y = baseline;
						_top += baseline - maxDescent;
						lastBaseline = baseline; 
					}
					_line = layoutResult[1];
				}
			}
		}
		return new Paragraph[] { newParagraph, rest };
	}
	
	public Line[] layout(Line line, Double left, Double top, Double right, Double bottom, ParagraphStyle paragraphStyle) {
		Line newLine = new Line(line);
		Line rest = null;
		Double _left = left;
		for (Run run : line.runs) {
			if (rest != null) {
				rest.runs.add(run);
			} else {
				Run[] layoutResult = layout(run, _left, top, right, bottom, paragraphStyle);
				if (layoutResult[0] != null) {
					newLine.runs.add(layoutResult[0]);
					if (_left != null) {
						layoutResult[0].setX(_left);
						_left += getStringWidth(layoutResult[0].getContent(), getCharacterStyle(layoutResult[0].getCharacterStyleName(), paragraphStyle));
					}
				}
				if (layoutResult[1] != null) {
					rest = new Line(line);
					rest.runs.add(layoutResult[1]);
				}
			}
		}
		return new Line[] { newLine, rest };
	}
	
	public Run[] layout(Run run, Double left, Double top, Double right, Double bottom, ParagraphStyle paragraphStyle) {
		CharacterStyle characterStyle = getCharacterStyle(run.getCharacterStyleName(), paragraphStyle);
		double width = (left != null && right != null) ? right - left : Double.POSITIVE_INFINITY;
		BreakIterator lineBreakIterator = getLineBreakIterator(run.getContent(), run.getLanguage());
		for (int lineEnd = lineBreakIterator.next(); lineEnd != BreakIterator.DONE; lineEnd = lineBreakIterator.next()) {
			if (getStringWidth(run.getContent().substring(0, lineEnd), characterStyle) > width) {
				int wordStart = lineBreakIterator.previous();
				int wordEnd = lineBreakIterator.next();
				String word = run.getContent().substring(wordStart, wordEnd);
				double hyphenationZone = width - getStringWidth(run.getContent().substring(0, wordStart), characterStyle);
				if (paragraphStyle.hyphenate && word.length() >= paragraphStyle.hyphenateWords­LongerThan && hyphenationZone > paragraphStyle.hyphenationZone && numHyphens < paragraphStyle.hyphenateLadder­Limit) {
					Hyphenation hyphenation = Hyphenator.hyphenate(run.getLanguage(), null, null, null, word, paragraphStyle.hyphenateAfter­First, paragraphStyle.hyphenateBeforeLast);
					if (hyphenation != null) {
						int[] hyphenationPoints = hyphenation.getHyphenationPoints();
						ArrayUtils.reverse(hyphenationPoints);
						for (int hyphenationPoint : hyphenationPoints) {
							if (getStringWidth(run.getContent().substring(0, wordStart + hyphenationPoint) + "-", characterStyle) <= width) {
								numHyphens++;
								return new Run[] {
										new Run(run, run.getContent().substring(0, wordStart + hyphenationPoint) + "-"),
										new Run(run, run.getContent().substring(wordStart + hyphenationPoint))
								};
							}
						}
					}
				}
				numHyphens = 0;
				return new Run[] {
						new Run(run, run.getContent().substring(0, wordStart)), 
						new Run(run, run.getContent().substring(wordStart)) };
			}
		}
		return new Run[] { new Run(run, run.getContent()), null };
	}
	
	private ParagraphStyle getParagraphStyle(String paragraphStyleName) {
		String cacheKey = "getParagraphStyle:" + paragraphStyleName;
		ParagraphStyle paragraphStyle = (ParagraphStyle) cache.get(cacheKey);
		if (paragraphStyle == null) {
			paragraphStyle = stylesheet.getParagraphStyle(paragraphStyleName);
			if (paragraphStyle == null) {
				// TODO paragraphStyle = stylesheet.getDefaultParagraphStyle();
			}
			cache.put(cacheKey, paragraphStyle);
		} else {
			cacheHits++;
		}
		return paragraphStyle;
	}

	private CharacterStyle getCharacterStyle(String characterStyleName, ParagraphStyle paragraphStyle) {
		String cacheKey = "getCharacterStyle:" + characterStyleName + "&" + paragraphStyle.getName();
		CharacterStyle characterStyle = (CharacterStyle) cache.get(cacheKey);
		if (characterStyle == null) {
			characterStyle = stylesheet.getCharacterStyle(characterStyleName);
			if (characterStyle == null) {
				characterStyle = stylesheet.getCharacterStyle(paragraphStyle.getCharacterStyleName());
			}
			cache.put(cacheKey, characterStyle);
		} else {
			cacheHits++;
		}
		return characterStyle;
	}
	
	private double getStringWidth(String string, CharacterStyle characterStyle) {
		try {
			String cacheKey = "getStringWidth:" + string + "&" + characterStyle.getName();
			Double stringWidth = (Double) cache.get(cacheKey);
			if (stringWidth == null) {
				PDFont font = getFont(characterStyle.getFontName());
				stringWidth = font.getStringWidth(string) * characterStyle.getFontSize() / 1000.0;
				cache.put(cacheKey, stringWidth);
			} else {
				cacheHits++;
			}
			return stringWidth;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private double getAscent(CharacterStyle characterStyle) {
		String cacheKey = "getAscent:" + characterStyle.getName();
		Double ascent = (Double) cache.get(cacheKey);
		if (ascent == null) {
			PDFont font = getFont(characterStyle.getFontName());
			PDFontDescriptor fontDescriptor = font.getFontDescriptor();
			ascent = fontDescriptor.getAscent() * characterStyle.getFontSize() / 1000.0;
			cache.put(cacheKey, ascent);
		} else {
			cacheHits++;
		}
		return ascent;
	}
	
	private double getDescent(CharacterStyle characterStyle) {
		String cacheKey = "getAscent:" + characterStyle.getName();
		Double descent = (Double) cache.get(cacheKey);
		if (descent == null) {
			PDFont font = getFont(characterStyle.getFontName());
			PDFontDescriptor fontDescriptor = font.getFontDescriptor();
			descent = fontDescriptor.getDescent() * characterStyle.getFontSize() / 1000.0;
			cache.put(cacheKey, descent);
		} else {
			cacheHits++;
		}
		return descent;
	}
	
	private PDFont getFont(String fontName) {
		PDFont font = fonts.get(fontName);
		if (font == null) {
			try {
				font = PDTrueTypeFont.loadTTF(document, fontName);
			} catch (Exception e) {
				font = PDType1Font.getStandardFont(fontName);
			}
			fonts.put(fontName, font);
		}
		return font;
	}
	
	private BreakIterator getLineBreakIterator(String text, String language) {
		BreakIterator lineBreakIterator = lineBreakIterators.get(language);
		if (lineBreakIterator == null) {
			lineBreakIterator = BreakIterator.getLineInstance(new Locale(language));
			lineBreakIterators.put(language, lineBreakIterator);
		}
		lineBreakIterator.setText(text);
		return lineBreakIterator;
	}
	
}
