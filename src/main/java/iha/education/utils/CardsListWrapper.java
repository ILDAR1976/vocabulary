package iha.education.utils;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import iha.education.entity.Cards;

@SuppressWarnings("serial")
@XmlRootElement(name = "vocabulary")
public class CardsListWrapper implements Serializable  {
    private List<Cards> cards;

    public CardsListWrapper() {
    	
    }

    public CardsListWrapper(List<Cards> cards) {
    	this.cards = cards;
    }
    @XmlElement(name = "card")
	public List<Cards> getCards() {
		return cards;
	}

	public void setCards(List<Cards> cards) {
		this.cards = cards;
	}

}
