package de.jworks.fpp.labs.layout;

import static org.junit.Assert.assertEquals;

import javax.xml.bind.JAXB;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.jworks.fpp.labs.story.Line;
import de.jworks.fpp.labs.story.Paragraph;
import de.jworks.fpp.labs.story.Run;
import de.jworks.fpp.labs.story.Story;
import de.jworks.fpp.labs.stylesheet.Stylesheet;

public class LayoutContextTest {
	
	private LayoutContext cut;

	@Before
	public void setUp() throws Exception {
		Stylesheet stylesheet = unmarshal("/stylesheet.xml", Stylesheet.class);
		cut = new LayoutContext(stylesheet);
	}

	@After
	public void tearDown() throws Exception {
		cut.close();
	}
	
	@Test
	public void testLayoutStory() throws Exception {
		Story story = unmarshal("/story.xml", Story.class);
		Story[] layoutResult = cut.layout(story, 0L, 0L, 200000L, 10000L);
		String expected = laadString("/story.txt");
		String actual = layoutResult[0].toString();
		assertEquals(expected, actual);
	}

	@Test
	public void testLayoutParagraph() throws Exception {
		Paragraph paragraph = unmarshal("/paragraph.xml", Paragraph.class);
		Paragraph[] layoutResult = cut.layout(paragraph, 0L, 0L, 200000L, 10000L);
		String expected = laadString("/paragraph.txt");
		String actual = layoutResult[0].toString();
		assertEquals(expected, actual);
	}

	@Test
	public void testLayoutLine() throws Exception {
		Line line = unmarshal("/line.xml", Line.class);
		Line[] layoutResult = cut.layout(line, 0L, 0L, 200000L, 10000L);
		String expected = laadString("/line.txt");
		String actual = layoutResult[0].toString();
		assertEquals(expected, actual);
	}

	@Test
	public void testLayoutRun() throws Exception {
		Run run = unmarshal("/run.xml", Run.class);
		Run[] layoutResult = cut.layout(run, 0L, 0L, 200000L, 10000L);
		String expected = laadString("/run.txt");
		String actual = layoutResult[0].toString();
		assertEquals(expected, actual);
	}

	private <T> T unmarshal(String resourceName, Class<T> type) {
		return JAXB.unmarshal(LayoutContextTest.class.getResourceAsStream(resourceName), type);
	}
	
	private String laadString(String resourceName) throws Exception {
		return IOUtils.toString(LayoutContextTest.class.getResourceAsStream(resourceName));
	}
	
}
