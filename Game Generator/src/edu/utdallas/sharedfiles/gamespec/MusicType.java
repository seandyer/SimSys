//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.04.25 at 07:45:04 PM CDT 
//


package edu.utdallas.sharedfiles.gamespec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MusicType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MusicType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Music" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MusicType", propOrder = {
    "music"
})
public class MusicType {

    @XmlElement(name = "Music", required = true)
    protected String music;

    /**
     * Gets the value of the music property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMusic() {
        return music;
    }

    /**
     * Sets the value of the music property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMusic(String value) {
        this.music = value;
    }

}
