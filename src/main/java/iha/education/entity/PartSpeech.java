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
public class PartSpeech implements Serializable {
	
	private Long id;
    private String name;
    private String translate;

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
	public String getWord() {
		return name;
	}
	
	public void setWord(String word) {
		this.name = word;
	}

	@Column(unique = true)
	public String getTranslate() {
		return translate;
	}

	public void setTranslate(String translate) {
		this.translate = translate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return  name;
	}
    
	
	
}
