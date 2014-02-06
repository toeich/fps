package de.jworks.fpp.labs.story;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Run {

	@XmlAttribute(name = "character-style")
	public String characterStyleName;

	@XmlAttribute
	public String language;

	@XmlValue
	public String content;

	public Run() {
	}

	public Run(Run prototype) {
		this.characterStyleName = prototype.characterStyleName;
		this.language = prototype.language;
	}

	public Run(Run prototype, String newContent) {
		this(prototype);
		this.content = newContent;
	}

	@Override
	public String toString() {
		return content;
	}

}
