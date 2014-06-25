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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Prop complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="Prop">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.example.org/Game/}GameElementType">
 *       &lt;sequence>
 *         &lt;element name="Hint" type="{http://www.example.org/Game/}HintType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Prop", propOrder = {
    "hint"
})
@XmlSeeAlso({
    GenericInteraction.class,
    Decoration.class,
    EducationInteraction.class
})
public class Prop
    extends GameElementType
{

    @XmlElement(name = "Hint", required = true)
    protected Hint hint;

    /**
     * Gets the value of the hint property.
     *
     * @return
     *     possible object is
     *     {@link HintType }
     *
     */
    public Hint getHint() {
        return hint;
    }

    /**
     * Sets the value of the hint property.
     *
     * @param value
     *     allowed object is
     *     {@link HintType }
     *
     */
    public void setHint(Hint value) {
        this.hint = value;
    }

}
