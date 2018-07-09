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
    private String word;
    private String translate;

    public PartSpeech() {
    }

    public PartSpeech(String word, String translate) {
    	this.word = word;
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
		return word;
	}
	
	public void setWord(String word) {
		this.word = word;
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
		result = prime * result + ((word == null) ? 0 : word.hashCode());
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
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		return true;
	}
    
	
}
