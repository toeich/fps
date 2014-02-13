package de.jworks.fpp.labs.story;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Line {

	@XmlElement(name = "run")
	public final List<Run> runs = new ArrayList<Run>();
	
	public Double y;

	public Line() {
	}

	public Line(Line prototype) {
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (Run run : runs) {
			buffer.append(run);
		}
		return buffer.toString();
	}

}
