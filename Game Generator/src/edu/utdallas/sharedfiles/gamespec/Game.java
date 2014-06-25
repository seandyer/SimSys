
//
//This file was generated by the JavaTM Architecture for XML Binding(JAXB)
//Reference Implementation, v2.2.4-2
//See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
//Any modifications to this file will be lost upon recompilation of the source
//schema.
//Generated on: 2014.04.25 at 07:45:04 PM CDT
//

package edu.utdallas.sharedfiles.gamespec;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
*<p>Java class for anonymous complex type.*
*<p>The following schema fragment specifies the expected content contained
*within this class.
* <pre>
* &lt;complexType>
* &lt;complexContent>
* &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
* &lt;sequence>
* &lt;element name="Act" type="{http://www.example.org/Game/}ActType" maxOccurs="unbounded"/>
* &lt;element name="LearningObjective" type="{http://www.example.org/Game/}LearningObjectiveType" maxOccurs="unbounded"/>
* &lt;element name="Character" type="{http://www.example.org/Game/}Character" maxOccurs="unbounded"/>
* &lt;/sequence>
* &lt;/restriction>
* &lt;/complexContent>
* &lt;/complexType>
* </pre>
*
*
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
 "act",
 "learningObjective",
 "character"
})
@XmlRootElement(name = "Game")
public class Game {

 @XmlElement(name = "Act", required = true)
 private List<Act> act;
 @XmlElement(name = "LearningObjective", required = true)
 private List<LearningObjectiveType> learningObjective;
 @XmlElement(name = "Character", required = true)
 private List<Character> character;

 /**
  * Gets the value of the act property.
  *
  * <p>
  * This accessor method returns a reference to the live list,
  * not a snapshot. Therefore any modification you make to the
  * returned list will be present inside the JAXB object.
  * This is why there is not a <CODE>set</CODE> method for the act property.
  *
  * <p>
  * For example, to add a new item, do as follows:
  * <pre>
  *    getAct().add(newItem);
  * </pre>
  *
  *
  * <p>
  * Objects of the following type(s) are allowed in the list
  * {@link ActType }
  *
  * @return the list of acts for the game
  */
 public final List<Act> getAct() {
     if (act == null) {
         act = new ArrayList<Act>();
     }
     return this.act;
 }

 /**
  * Gets the value of the learningObjective property.
  *
  * <p>
  * This accessor method returns a reference to the live list,
  * not a snapshot. Therefore any modification you make to the
  * returned list will be present inside the JAXB object.
  * This is why there is not a <CODE>set</CODE> method for the
  * learningObjective property.
  * <p>
  * For example, to add a new item, do as follows:
  * <pre>
  *    getLearningObjective().add(newItem);
  * </pre>
  *
  *
  * <p>
  * Objects of the following type(s) are allowed in the list
  * {@link LearningObjectiveType }
  *
  * @return the list of LearningObjectives
  */
 public final List<LearningObjectiveType> getLearningObjective() {
     if (learningObjective == null) {
         learningObjective = new ArrayList<LearningObjectiveType>();
     }
     return this.learningObjective;
 }

 /**
  * Gets the value of the character property.
  *
  * <p>
  * This accessor method returns a reference to the live list,
  * not a snapshot. Therefore any modification you make to the
  * returned list will be present inside the JAXB object.
  * This is why there is not a <CODE>set</CODE> method for the
  * character property.
  *
  * <p>
  * For example, to add a new item, do as follows:
  * <pre>
  *    getCharacter().add(newItem);
  * </pre>
  *
  *
  * <p>
  * Objects of the following type(s) are allowed in the list
  * {@link Character }
  *
  * @return the list of Character objects for the game
  */
 public final List<Character> getCharacter() {
     if (character == null) {
         character = new ArrayList<Character>();
     }
     return this.character;
 }

}
