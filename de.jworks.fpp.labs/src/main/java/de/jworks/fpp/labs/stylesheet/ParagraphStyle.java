package de.jworks.fpp.labs.stylesheet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class ParagraphStyle {

	@XmlAttribute
	private String name;
	
	@XmlAttribute(name = "character-style")
	private String characterStyleName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCharacterStyleName() {
		return characterStyleName;
	}

	public void setCharacterStyleName(String characterStyleName) {
		this.characterStyleName = characterStyleName;
	}
	
}
