package iha.education.utils;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import iha.education.entity.Cards;
import iha.education.entity.PartSpeech;
import iha.education.entity.SenseGroup;
import iha.education.entity.SubGroup;

@SuppressWarnings("serial")
@XmlRootElement(name = "vocabulary")
public class CardsListWrapper implements Serializable  {
    
	private List<Cards> cards;
	private List<PartSpeech> partSpeech;
	private List<SenseGroup> senseGroup;
	private List<SubGroup> subGroup;
	

    public CardsListWrapper() {
    	
    }

    public CardsListWrapper(List<Cards> cards, List<PartSpeech> partSpeech, List<SenseGroup> senseGroup, List<SubGroup> subGroup) {
    	this.cards = cards;
    	this.partSpeech = partSpeech;
    	this.senseGroup = senseGroup;
    	this.subGroup = subGroup;
    }
    
    @XmlElement(name = "card")
	public List<Cards> getCards() {
		return cards;
	}

	public void setCards(List<Cards> cards) {
		this.cards = cards;
	}
	
	@XmlElement(name = "part_speech")
	public List<PartSpeech> getPartSpeech() {
		return partSpeech;
	}

	public void setPartSpeech(List<PartSpeech> partSpeech) {
		this.partSpeech = partSpeech;
	}

	@XmlElement(name = "sense_group")
	public List<SenseGroup> getSenseGroup() {
		return senseGroup;
	}

	public void setSenseGroup(List<SenseGroup> senseGroup) {
		this.senseGroup = senseGroup;
	}
	
	@XmlElement(name = "sub_group")
	public List<SubGroup> getSubGroup() {
		return subGroup;
	}

	public void setSubGroup(List<SubGroup> subGroup) {
		this.subGroup = subGroup;
	}
	
	

}
