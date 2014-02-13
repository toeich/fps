package de.jworks.fpp.labs.layout;

import static org.junit.Assert.assertEquals;

import javax.xml.bind.JAXB;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.jworks.fpp.labs.story.Story;
import de.jworks.fpp.labs.stylesheet.Stylesheet;

public class LayoutContextTest {
	
	private static final double MM = 72.0 / 25.4;
	
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
		Story[] layoutResult = cut.layout(story, 0.0, 0.0, 50.0 * MM, 50.0 * MM);
		String expected = laadString("/story.txt");
		String actual = layoutResult[0].toString();
		System.out.println(actual);
		assertEquals(expected, actual);
	}

	private <T> T unmarshal(String resourceName, Class<T> type) {
		return JAXB.unmarshal(LayoutContextTest.class.getResourceAsStream(resourceName), type);
	}
	
	private String laadString(String resourceName) throws Exception {
		return IOUtils.toString(LayoutContextTest.class.getResourceAsStream(resourceName));
	}
	
}
