package de.jworks.fpp.labs.layout;

import static org.junit.Assert.fail;

import javax.xml.bind.JAXB;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.jworks.fpp.labs.story.Run;
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
	public void testLayoutStory() {
		fail("Not yet implemented");
	}

	@Test
	public void testLayoutParagraph() {
		fail("Not yet implemented");
	}

	@Test
	public void testLayoutLine() {
		fail("Not yet implemented");
	}

	@Test
	public void testLayoutRun() {
		Run run = unmarshal("/run.xml", Run.class);
		Run[] layoutResult = cut.layout(run, 0L, 0L, 200000L, 10000L);
		System.out.println(layoutResult[0]);
		fail("Not yet implemented");
	}

	private <T> T unmarshal(String name, Class<T> type) {
		return JAXB.unmarshal(LayoutContextTest.class.getResourceAsStream(name), type);
	}
	
}
