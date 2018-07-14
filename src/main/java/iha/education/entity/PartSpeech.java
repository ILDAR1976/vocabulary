package iha.education.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Akhmetov Ildar (github/ILDAR1976)
 *
 **/

@Entity
@Table
@NamedQueries({
    @NamedQuery(name="PartSpeech.findByName", query="select ps from PartSpeech ps where ps.name=:name")
})
public class PartSpeech implements Serializable {
	
	private Long id;
    private String name;
    private String translate;
    private Set<Cards> cards = new HashSet<Cards>();
    
    public PartSpeech() {
    }

    public PartSpeech(String name, String translate) {
    	this.name = name;
    	this.translate = translate;
    }
    
    @Id
	@GeneratedValue
 	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	@Column(unique = true)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Column(unique = true)
	public String getTranslate() {
		return translate;
	}

	public void setTranslate(String translate) {
		this.translate = translate;
	}

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "partSpeech")
	public Set<Cards> getCards() {
		return cards;
	}

	public void setCards(Set<Cards> cards) {
		this.cards = cards;
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
