package iha.education.entity;

import javax.persistence.*;


import java.io.Serializable;

/**
 *
 * @author Akhmetov Ildar (github/ILDAR1976)
 *
 **/

@Entity
@Table
@NamedQueries({
    @NamedQuery(name="Cards.findByWord", query="select c from Cards c where c.word=:word")
})
public class Cards implements Serializable {
	
	private Long id;
	private PartSpeech partSpeech;
	private SenseGroup senseGroup;
	private SubGroup subGroup;
    private String word;
    private String translate;
    private String example;
    private Boolean modificated;
    
    public Cards() {
    }

    public Cards(String word, String translate, String example) {
    	super();
    	this.word = word;
		this.translate = translate;
		this.example = example;
    	
    }

    
    public Cards(PartSpeech partSpeech, SenseGroup senseGroup, SubGroup subGroup, String word, String translate,
			String example) {
		this(word, translate, example);
		this.partSpeech = partSpeech;
		this.senseGroup = senseGroup;
		this.subGroup = subGroup;
	}

	@Id
	@GeneratedValue
 	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "partSpeech_id", nullable = false)
	public PartSpeech getPartSpeech() {
		return partSpeech;
	}

	public void setPartSpeech(PartSpeech partSpeech) {
		this.partSpeech = partSpeech;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "senseGroup_id", nullable = false)
	public SenseGroup getSenseGroup() {
		return senseGroup;
	}

	public void setSenseGroup(SenseGroup senseGroup) {
		this.senseGroup = senseGroup;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subGroup_id", nullable = false)
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
