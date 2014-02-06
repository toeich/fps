
package de.jworks.fpp.labs.indesign.soap;

import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Object complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Object">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="specifierData" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *         &lt;element name="objectType" type="{http://www.w3.org/2001/XMLSchema}unsignedLong"/>
 *         &lt;element name="specifierForm" type="{http://www.w3.org/2001/XMLSchema}unsignedLong"/>
 *         &lt;element name="start" type="{http://ns.adobe.com/InDesign/soap/}Object" minOccurs="0"/>
 *         &lt;element name="end" type="{http://ns.adobe.com/InDesign/soap/}Object" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Object", propOrder = {
    "specifierData",
    "objectType",
    "specifierForm",
    "start",
    "end"
})
public class Object {

    @XmlElement(required = true)
    protected java.lang.Object specifierData;
    @XmlElement(required = true)
    @XmlSchemaType(name = "unsignedLong")
    protected BigInteger objectType;
    @XmlElement(required = true)
    @XmlSchemaType(name = "unsignedLong")
    protected BigInteger specifierForm;
    @XmlElementRef(name = "start", type = JAXBElement.class, required = false)
    protected JAXBElement<Object> start;
    @XmlElementRef(name = "end", type = JAXBElement.class, required = false)
    protected JAXBElement<Object> end;

    /**
     * Gets the value of the specifierData property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.Object }
     *     
     */
    public java.lang.Object getSpecifierData() {
        return specifierData;
    }

    /**
     * Sets the value of the specifierData property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.Object }
     *     
     */
    public void setSpecifierData(java.lang.Object value) {
        this.specifierData = value;
    }

    /**
     * Gets the value of the objectType property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getObjectType() {
        return objectType;
    }

    /**
     * Sets the value of the objectType property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setObjectType(BigInteger value) {
        this.objectType = value;
    }

    /**
     * Gets the value of the specifierForm property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSpecifierForm() {
        return specifierForm;
    }

    /**
     * Sets the value of the specifierForm property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSpecifierForm(BigInteger value) {
        this.specifierForm = value;
    }

    /**
     * Gets the value of the start property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Object }{@code >}
     *     
     */
    public JAXBElement<Object> getStart() {
        return start;
    }

    /**
     * Sets the value of the start property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Object }{@code >}
     *     
     */
    public void setStart(JAXBElement<Object> value) {
        this.start = value;
    }

    /**
     * Gets the value of the end property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Object }{@code >}
     *     
     */
    public JAXBElement<Object> getEnd() {
        return end;
    }

    /**
     * Sets the value of the end property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Object }{@code >}
     *     
     */
    public void setEnd(JAXBElement<Object> value) {
        this.end = value;
    }

}
