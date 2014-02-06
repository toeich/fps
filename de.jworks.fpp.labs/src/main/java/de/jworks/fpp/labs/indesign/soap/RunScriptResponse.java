
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
 *         &lt;element name="errorNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="errorString" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="scriptResult" type="{http://ns.adobe.com/InDesign/soap/}Data" minOccurs="0"/>
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
    "errorNumber",
    "errorString",
    "scriptResult"
})
@XmlRootElement(name = "RunScriptResponse")
public class RunScriptResponse {

    protected int errorNumber;
    @XmlElementRef(name = "errorString", type = JAXBElement.class, required = false)
    protected JAXBElement<String> errorString;
    @XmlElementRef(name = "scriptResult", type = JAXBElement.class, required = false)
    protected JAXBElement<Data> scriptResult;

    /**
     * Gets the value of the errorNumber property.
     * 
     */
    public int getErrorNumber() {
        return errorNumber;
    }

    /**
     * Sets the value of the errorNumber property.
     * 
     */
    public void setErrorNumber(int value) {
        this.errorNumber = value;
    }

    /**
     * Gets the value of the errorString property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getErrorString() {
        return errorString;
    }

    /**
     * Sets the value of the errorString property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setErrorString(JAXBElement<String> value) {
        this.errorString = value;
    }

    /**
     * Gets the value of the scriptResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Data }{@code >}
     *     
     */
    public JAXBElement<Data> getScriptResult() {
        return scriptResult;
    }

    /**
     * Sets the value of the scriptResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Data }{@code >}
     *     
     */
    public void setScriptResult(JAXBElement<Data> value) {
        this.scriptResult = value;
    }

}
