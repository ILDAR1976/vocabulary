package iha.education.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import iha.education.entity.WSLongAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@XmlRootElement()
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table
@NamedQueries({
    @NamedQuery(name="PartSpeech.findByName", query="select ps from PartSpeech ps where ps.name=:name")
  
})
public class PartSpeech implements Serializable {

	private static final long serialVersionUID = -2626530500508744754L;
	@XmlElement
	@XmlID
    @XmlJavaTypeAdapter(WSLongAdapter.class)
	private Long id;
	@XmlElement
    private String name;
	@XmlElement
	private String translate;
	@XmlElementWrapper
    private List<Cards> cards = new ArrayList<>();
	private Boolean modificated;
	
    public PartSpeech() {
    }

    public PartSpeech(String name, String translate) {
    	this.name = name;
    	this.translate = translate;
    }
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
 	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	@Column
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Column
	public String getTranslate() {
		return translate;
	}

	public void setTranslate(String translate) {
		this.translate = translate;
	}

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "partSpeech")
	public List<Cards> getCards() {
		return cards;
	}

	public void setCards(List<Cards> cards) {
		this.cards = cards;
	}
	
	public Boolean getModificated() {
		return modificated;
	}

	public void setModificated(Boolean modificated) {
		this.modificated = modificated;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PartSpeech other = (PartSpeech) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return  name;
	}
    
	
	
}
