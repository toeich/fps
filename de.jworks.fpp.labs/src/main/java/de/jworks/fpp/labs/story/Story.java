package de.jworks.fpp.labs.story;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Story {

	@XmlElement(name = "paragraph")
	public final List<Paragraph> paragraphs = new ArrayList<Paragraph>();

	public Story() {
	}
	
	public Story(Story prototype) {
	}
	
}
