package edu.utdallas.sharedfiles.Shared;

import edu.utdallas.gamegenerator.Characters.GameCharacter;
import edu.utdallas.gamegenerator.LearningAct.Character.LearningActCharacter;
import edu.utdallas.gamegenerator.LearningAct.Prop.GameButton;
import edu.utdallas.gamegenerator.LearningAct.Prop.GameText;
import edu.utdallas.gamegenerator.Locale.ObjectMovement;
import edu.utdallas.gamespecification.Hint;

import javax.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * User: clocke
 * Date: 3/3/13
 * Time: 8:50 PM
 */
@XmlRootElement(name = "AssetBase")
@XmlSeeAlso({ImageAsset.class, ButtonAsset.class, InformationBoxAsset.class, CharacterAsset.class, ConversationBubbleAsset.class, ThoughtBubbleAsset.class})
public class Asset implements Cloneable {
    private String type;
    private UUID id;
    private String name;
    private List<Behavior> behaviors;
    private int opacity = 1;
    private int x;
    private int y;
    private int x2;
    private int y2;
    private int width;
    private int height;
    private Color backgroundColor;
    private Color foregroundColor;
    private int fontSize = 15;
    private String fontFamily = "Comic Sans MS";
    private int experience;
    private int certificates;
    private int promotions;
    private int trophies;
    private int points;
    private Hint hint;
    private String displayImage;
    private String soundEffect;

    public Asset() {
        id = UUID.randomUUID();
    }

    public Asset(Asset asset) {
//        setType(asset.getType());
        setId(asset.getId());
        setName(asset.getName());
        setBehaviors(asset.getBehaviors());
        setOpacity(asset.getOpacity());
        setX(asset.getX());
        setY(asset.getY());
        setX2(asset.getX2());
        setY2(asset.getY2());
        setWidth(asset.getWidth());
        setHeight(asset.getHeight());
        setBackgroundColor(asset.getBackgroundColor());
        setForegroundColor(asset.getForegroundColor());
        setFontSize(asset.getFontSize());
        setFontFamily(asset.getFontFamily());
        setExperience(asset.getExperience());
        setCertificates(asset.getCertificates());
        setPromotions(asset.getPromotions());
        setTrophies(asset.getTrophies());
        setPoints(asset.getPoints());
        setHint(asset.getHint());
        setDisplayImage(asset.getDisplayImage());
    }

    public Asset(GameObject gameObject) {
        id = UUID.randomUUID();
        type = "ImageAsset";
        x = gameObject.getX();
        y = gameObject.getY();
        width = gameObject.getWidth();
        height = gameObject.getHeight();
        x2 = x + width;
        y2 = y + height;
        displayImage = gameObject.getPathToAsset();
    }

    public Asset(SharedCharacter character, GameCharacter gameCharacter,
                 LearningActCharacter learningActCharacter) {
        id = UUID.randomUUID();
        type = "CharacterAsset";
        x = character.getX();
        y = character.getY();
        width = character.getWidth();
        height = character.getHeight();
        x2 = x + width;
        y2 = y + height;
        displayImage = gameCharacter.getCharacterAsset(character.getCharacterAssetType());
        behaviors = new ArrayList<Behavior>();
        for(ObjectMovement movement : character.getMovements()) {
            if(learningActCharacter.getMovementType() == movement.getMovementType()) {
                behaviors.add(new Behavior(movement));
            }
        }
    }

    public Asset(SharedCharacter character, GameCharacter gameCharacter) {
        id = UUID.randomUUID();
        type = "CharacterAsset";
        x = character.getX();
        y = character.getY();
        width = character.getWidth();
        height = character.getHeight();
        x2 = x + width;
        y2 = y + height;
        displayImage = gameCharacter.getCharacterAsset(character.getCharacterAssetType());
        behaviors = new ArrayList<Behavior>();
        if(character.getMovements() != null) {
            for(ObjectMovement movement : character.getMovements()) {
                behaviors.add(new Behavior(movement));
            }
        }
    }

    public Asset(SharedButton button) {
        this((GameObject)button);
        this.name = button.getName();
        type = "ButtonAsset";
        this.behaviors = new ArrayList<Behavior>();
        this.behaviors.add(button.getBehavior());
    }

    public Asset(SharedInformationBox informationBox) {
        this((GameObject)informationBox);
        this.name = informationBox.getName();
        type = "InformationBoxAsset";
    }

    public Asset(SharedInformationBox informationBox, GameText gameText) {
        this(informationBox);
        this.name = gameText.getText();
    }

    public Asset(SharedButton sharedButton, GameButton gameButton) {
        this(sharedButton);
        this.name = gameButton.getText();
        behaviors = new ArrayList<Behavior>();
        if(gameButton.getReward() != null) {
            Behavior behavior = new Behavior();
            behavior.setBehaviorType(BehaviorType.REWARD_BEHAVIOR);
            behavior.setPoints(gameButton.getReward().getPoints());

            behaviors.add(behavior);
        }
        if(gameButton.getTransitionType() != null) {
            Behavior behavior = new Behavior();
            behavior.setBehaviorType(BehaviorType.TRANSITION_BEHAVIOR);
            behaviors.add(behavior);
        }
    }
    
    public Object clone(){  
        try{  
            return super.clone();  
        }catch(Exception e){ 
            return null; 
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlElement(name = "ID")
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @XmlElement(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElementWrapper(name = "BehaviorList")
    @XmlElement(name = "Behavior")
    public List<Behavior> getBehaviors() {
        return behaviors;
    }

    public void setBehaviors(List<Behavior> behaviors) {
        this.behaviors = behaviors;
    }

    @XmlElement(name = "Opacity")
    public int getOpacity() {
        return opacity;
    }

    public void setOpacity(int opacity) {
        this.opacity = opacity;
    }

    @XmlElement(name = "X")
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @XmlElement(name = "Y")
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @XmlElement(name = "X2")
    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    @XmlElement(name = "Y2")
    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    @XmlElement(name = "Width")
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @XmlElement(name = "Height")
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @XmlElement(name = "BackgroundColor")
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    @XmlElement(name = "ForegroundColor")
    public Color getForegroundColor() {
        return foregroundColor;
    }

    public void setForegroundColor(Color foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    @XmlElement(name = "FontSize")
    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    @XmlElement(name = "FontFamily")
    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

//    @XmlElement(name = "Experience")
    @XmlTransient
    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

//    @XmlElement(name = "Certificates")
    @XmlTransient
    public int getCertificates() {
        return certificates;
    }

    public void setCertificates(int certificates) {
        this.certificates = certificates;
    }

//    @XmlElement(name = "Promotions")
    @XmlTransient
    public int getPromotions() {
        return promotions;
    }

    public void setPromotions(int promotions) {
        this.promotions = promotions;
    }

//    @XmlElement(name = "Trophies")
    @XmlTransient
    public int getTrophies() {
        return trophies;
    }

    public void setTrophies(int trophies) {
        this.trophies = trophies;
    }

//    @XmlElement(name = "Points")
    @XmlTransient
    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

//    @XmlElement(name = "Hint")
    @XmlTransient
    public Hint getHint() {
        return hint;
    }

    public void setHint(Hint hint) {
        this.hint = hint;
    }

    @XmlElement(name = "DisplayImage")
    public String getDisplayImage() {
        return displayImage;
    }

    public void setDisplayImage(String displayImage) {
        this.displayImage = displayImage;
    }
    
    @XmlElement(name = "SoundEffect")
	public String getSoundEffect() {
		return soundEffect;
	}
    
    public void setSoundEffect(String soundEffect) {
    	this.soundEffect = soundEffect;
	}
}
