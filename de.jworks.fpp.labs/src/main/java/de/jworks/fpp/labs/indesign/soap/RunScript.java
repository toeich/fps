
package de.jworks.fpp.labs.indesign.soap;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="runScriptParameters" type="{http://ns.adobe.com/InDesign/soap/}RunScriptParameters" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "runScriptParameters"
})
@XmlRootElement(name = "RunScript")
public class RunScript {

    @XmlElementRef(name = "runScriptParameters", type = JAXBElement.class, required = false)
    protected JAXBElement<RunScriptParameters> runScriptParameters;

    /**
     * Gets the value of the runScriptParameters property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link RunScriptParameters }{@code >}
     *     
     */
    public JAXBElement<RunScriptParameters> getRunScriptParameters() {
        return runScriptParameters;
    }

    /**
     * Sets the value of the runScriptParameters property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link RunScriptParameters }{@code >}
     *     
     */
    public void setRunScriptParameters(JAXBElement<RunScriptParameters> value) {
        this.runScriptParameters = value;
    }

}
