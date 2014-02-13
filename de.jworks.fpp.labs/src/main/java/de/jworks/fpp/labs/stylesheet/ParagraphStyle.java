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
	
	@XmlAttribute
	private Double leading = null;

	// InDesign Default
	public boolean hyphenate = true;
	public int hyphenateWords­LongerThan = 5;
	public int hyphenateAfter­First = 2;
	public int hyphenateBeforeLast = 2;
	public int hyphenateLadder­Limit = 3;
	public double hyphenationZone = 12.7*72.0/25.4; // 12.7 mm
	public boolean hyphenate­CapitalizedWords = true;
	
//	// QuarkXPress Default
//	public boolean hyphenate = true;
//	public int hyphenateWords­LongerThan = 6;
//	public int hyphenateAfter­First = 2;
//	public int hyphenateBeforeLast = 3;
//	public int hyphenateLadder­Limit = 2;
//	public double hyphenationZone = 0.0*72.0/25.4; // 0.0 mm
//	public boolean hyphenate­CapitalizedWords = true;

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

	public Double getLeading() {
		return leading;
	}

	public void setLeading(Double leading) {
		this.leading = leading;
	}
	
}
