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
	private String characterStyleName;

	@XmlAttribute
	private String language;

	@XmlValue
	private String content;

	private Double x;

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

	public String getCharacterStyleName() {
		return characterStyleName;
	}

	public void setCharacterStyleName(String characterStyleName) {
		this.characterStyleName = characterStyleName;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	@Override
	public String toString() {
		return getContent();
	}

}
