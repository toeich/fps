
package de.jworks.fpp.labs.indesign.soap;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RunScriptParameters complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RunScriptParameters">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="scriptText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="scriptLanguage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="scriptFile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="scriptArgs" type="{http://ns.adobe.com/InDesign/soap/}IDSP-ScriptArg" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RunScriptParameters", propOrder = {
    "scriptText",
    "scriptLanguage",
    "scriptFile",
    "scriptArgs"
})
public class RunScriptParameters {

    @XmlElementRef(name = "scriptText", type = JAXBElement.class, required = false)
    protected JAXBElement<String> scriptText;
    @XmlElementRef(name = "scriptLanguage", type = JAXBElement.class, required = false)
    protected JAXBElement<String> scriptLanguage;
    @XmlElementRef(name = "scriptFile", type = JAXBElement.class, required = false)
    protected JAXBElement<String> scriptFile;
    protected List<IDSPScriptArg> scriptArgs;

    /**
     * Gets the value of the scriptText property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getScriptText() {
        return scriptText;
    }

    /**
     * Sets the value of the scriptText property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setScriptText(JAXBElement<String> value) {
        this.scriptText = value;
    }

    /**
     * Gets the value of the scriptLanguage property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getScriptLanguage() {
        return scriptLanguage;
    }

    /**
     * Sets the value of the scriptLanguage property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setScriptLanguage(JAXBElement<String> value) {
        this.scriptLanguage = value;
    }

    /**
     * Gets the value of the scriptFile property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getScriptFile() {
        return scriptFile;
    }

    /**
     * Sets the value of the scriptFile property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setScriptFile(JAXBElement<String> value) {
        this.scriptFile = value;
    }

    /**
     * Gets the value of the scriptArgs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the scriptArgs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getScriptArgs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IDSPScriptArg }
     * 
     * 
     */
    public List<IDSPScriptArg> getScriptArgs() {
        if (scriptArgs == null) {
            scriptArgs = new ArrayList<IDSPScriptArg>();
        }
        return this.scriptArgs;
    }

}
