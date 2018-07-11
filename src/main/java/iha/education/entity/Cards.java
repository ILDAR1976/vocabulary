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
    //@NamedQuery(name="update.updateAll", query="select count(c) from Contact c")
    //@NamedQuery(name="Cards.updateAll", query="update Cards set example=:example, modificated=:modificated, partSpeech=:partSpeech, senseGroup=:senseGroup, subGroup=:subGroup, translate=:translate, word=:word where id=:id")
    @NamedQuery(name="Cards.findAllFromPartSpeech", query="select ps from PartSpeech ps")
})

public class Cards implements Serializable {
	
	private Long id;
	
	@ManyToOne(targetEntity = PartSpeech.class, cascade = CascadeType.ALL)
	@JoinTable(name = "PartSpeech", 
    joinColumns = @JoinColumn(name = "id"), 
    inverseJoinColumns = @JoinColumn(name = "partSpeech"))
    private PartSpeech partSpeech;

    @ManyToOne(targetEntity = SenseGroup.class)
    @JoinColumn(name = "id")
    private SenseGroup senseGroup;

    @ManyToOne(targetEntity = SubGroup.class)
    @JoinColumn(name = "id")
    private SubGroup subGroup;
    
    private String word;
    private String translate;
    private String example;
    private Boolean modificated;
    
    public Cards() {
    }

    public Cards(PartSpeech partSpeech, SenseGroup senseGroup, SubGroup subGroup, String word, String translate,
			String example) {
		super();
		this.partSpeech = partSpeech;
		this.senseGroup = senseGroup;
		this.subGroup = subGroup;
		this.word = word;
		this.translate = translate;
		this.example = example;
	}



	@Id
	@GeneratedValue
 	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PartSpeech getPartSpeech() {
		return partSpeech;
	}

	public void setPartSpeech(PartSpeech partSpeech) {
		this.partSpeech = partSpeech;
	}

	public SenseGroup getSenseGroup() {
		return senseGroup;
	}

	public void setSenseGroup(SenseGroup senseGroup) {
		this.senseGroup = senseGroup;
	}

	public SubGroup getSubGroup() {
		return subGroup;
	}

	public void setSubGroup(SubGroup subGroup) {
		this.subGroup = subGroup;
	}

	@Column(unique = true)
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
