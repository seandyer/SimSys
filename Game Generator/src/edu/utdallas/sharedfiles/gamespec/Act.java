package edu.utdallas.sharedfiles.gamespec;

//
//This file was generated by the JavaTM Architecture for XML Binding(JAXB)
//Reference Implementation, v2.2.4-2
//See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
//Any modifications to this file will be lost upon recompilation of the source schema.
//Generated on: 2014.04.25 at 07:45:04 PM CDT
//


//package org.example.game;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
* <p>Java class for ActType complex type.
*
* <p>The following schema fragment specifies the expected content contained within this class.
*
* <pre>
* &lt;complexType name="ActType">
*   &lt;complexContent>
*     &lt;extension base="{http://www.example.org/Game/}GameUnitType">
*       &lt;sequence>
*         &lt;element name="LearningObjective" type="{http://www.example.org/Game/}LearningObjectiveType"/>
*         &lt;element name="Scene" type="{http://www.example.org/Game/}SceneType" maxOccurs="unbounded"/>
*       &lt;/sequence>
*     &lt;/extension>
*   &lt;/complexContent>
* &lt;/complexType>
* </pre>
*
*
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ActType", propOrder = {
 "learningObjective",
 "scene"
})
public class Act extends GameUnitType {

 @XmlElement(name = "LearningObjective", required = true)
 private LearningObjectiveType learningObjective;
 @XmlElement(name = "Scene", required = true)
 private List<Scene> scene;


/**
  * Gets the value of the learningObjective property.
  *
  * @return
  *     possible object is
  *     {@link LearningObjectiveType }
  *
  */
 public final LearningObjectiveType getLearningObjective() {
     return learningObjective;
 }

 /**
  * Sets the value of the learningObjective property.
  *
  * @param value
  *     allowed object is
  *     {@link LearningObjectiveType }
  *
  */
 public final void setLearningObjective(final LearningObjectiveType value) {
     this.learningObjective = value;
 }

 /**
  * Gets the value of the scene property.
  *
  * <p>
  * This accessor method returns a reference to the live list,
  * not a snapshot. Therefore any modification you make to the
  * returned list will be present inside the JAXB object.
  * This is why there is not a <CODE>set</CODE> method for the scene property.
  *
  * <p>
  * For example, to add a new item, do as follows:
  * <pre>
  *    getScene().add(newItem);
  * </pre>
  *
  *
  * <p>
  * @return
  *      possible object is
  *      {@link SceneType }
  *
  *@return the list of scenes for the act
  */
 public final List<Scene> getScene() {
     if (scene == null) {
         scene = new ArrayList<Scene>();
     }
     return this.scene;
 }
// Sets list of scenes for the act
 public void setScene(List<Scene> scene) {
	this.scene = scene;
}

}
