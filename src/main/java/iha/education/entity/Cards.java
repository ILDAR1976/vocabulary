package iha.education.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import iha.education.entity.WSLongAdapter;
import iha.education.ui.CardsController;

import java.io.Serializable;

/**
 *
 * @author Akhmetov Ildar (github/ILDAR1976)
 *
 **/

@XmlRootElement()
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table
@NamedQueries({
    @NamedQuery(name="Cards.findByWord", query="select c from Cards c where c.word=:word"),
    @NamedQuery(name="Cards.findByThirdFilter", query="select c from Cards c where c.partSpeech=:partSpeech and "
    																			 + "c.senseGroup=:senseGroup and "
    																			 + "c.subGroup=:subGroup"),
    @NamedQuery(name="Cards.findByFifthFilterLike", query="select c from Cards c where"
    		 + " c.partSpeech=:partSpeech and"
			 + " c.senseGroup=:senseGroup and"
			 + " c.subGroup=:subGroup and"
			 + " c.word like :word and"
			 + " c.translate like :translate"
    		 )

})

public class Cards implements Serializable {
	
	private static final long serialVersionUID = 1776649399856633994L;

	@XmlElement
	@XmlID
    @XmlJavaTypeAdapter(WSLongAdapter.class)
	private Long id;
	@XmlIDREF
	private PartSpeech partSpeech;
	@XmlIDREF
	private SenseGroup senseGroup;
	@XmlIDREF
	private SubGroup subGroup;
	@XmlElement
	private String word;
	@XmlElement
	private String translate;
	@XmlElement
	private String example;
    private String checkWord;
	private Boolean modificated;
   
    
    public Cards() {
    }

    public Cards(String word, String translate, String example) {
    	super();
    	this.word = word;
		this.translate = translate;
		this.example = example;
    	//logger.info("Cards 2 created ...");
    }
   
    public Cards(PartSpeech partSpeech, SenseGroup senseGroup, SubGroup subGroup, String word, String translate,
			String example) {
		this(word, translate, example);
		this.partSpeech = partSpeech;
		this.senseGroup = senseGroup;
		this.subGroup = subGroup;
		//logger.info("Cards 3 created ...");
	}

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
 	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "partspeech_id", nullable = false)
	public PartSpeech getPartSpeech() {
		return partSpeech;
	}

	public void setPartSpeech(PartSpeech partSpeech) {
		this.partSpeech = partSpeech;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sensegroup_id", nullable = false)
	public SenseGroup getSenseGroup() {
		return senseGroup;
	}

	public void setSenseGroup(SenseGroup senseGroup) {
		this.senseGroup = senseGroup;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "subgroup_id", nullable = false)
	public SubGroup getSubGroup() {
		return subGroup;
	}

	public void setSubGroup(SubGroup subGroup) {
		this.subGroup = subGroup;
	}

	@Column
	public String getWord() {
		return word;
	}
	
	public void setWord(String word) {
		this.word = word;
	}

	public String getTranslate() {
		return translate;
	}

	public void setTranslate(String translate) {
		this.translate = translate;
	}

	@Column
	public String getExample() {
		return example;
	}

	public void setExample(String example) {
		this.example = example;
	}
	
	@Column
	public String getCheckWord() {
		return checkWord;
	}

	public void setCheckWord(String check) {
		this.checkWord = check;
	}

	@Column
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
		Cards other = (Cards) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
