package de.jworks.fpp.labs.stylesheet;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Stylesheet {

	@XmlAttribute
	private String name;
	
	@XmlElement(name = "color")
	@XmlElementWrapper(name = "colors")
	private final List<Color> colors = new ArrayList<Color>();
	
	@XmlElement(name = "character-style")
	@XmlElementWrapper(name = "character-styles")
	private final List<CharacterStyle> characterStyles = new ArrayList<CharacterStyle>();

	@XmlElement(name = "paragraph-style")
	@XmlElementWrapper(name = "paragraph-styles")
	private final List<ParagraphStyle> paragraphStyles = new ArrayList<ParagraphStyle>();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<Color> getColors() {
		return colors;
	}

	public List<CharacterStyle> getCharacterStyles() {
		return characterStyles;
	}
	
	public CharacterStyle getCharacterStyle(String name) {
		for (CharacterStyle characterStyle : characterStyles) {
			if (characterStyle.getName().equals(name)) {
				return characterStyle;
			}
		}
		return null;
	}
	
}
