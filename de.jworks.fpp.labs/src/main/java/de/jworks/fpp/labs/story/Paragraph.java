package de.jworks.fpp.labs.story;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Paragraph {
	
	@XmlAttribute(name = "paragraph-style")
	public String paragraphStyleName;

	@XmlElement(name = "line")
	public final List<Line> lines = new ArrayList<Line>();

	public Paragraph() {
	}
	
	public Paragraph(Paragraph prototype) {
		this.paragraphStyleName = prototype.paragraphStyleName;
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (Line line : lines) {
			buffer.append(line).append("\n");
		}
		return buffer.toString();
	}
	
}
