package iha.education.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
@NamedQueries({
    @NamedQuery(name="SenseGroup.findByName", query="select sg from SenseGroup sg where sg.name=:name")
})
public class SenseGroup implements Serializable  {
	private Long id;
    private String name;
    private String translate;
 
    private Set<Cards> cards = new HashSet<Cards>();

    SenseGroup() {}
 
    public SenseGroup(String name, String translate) {
		super();
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
	
	@Column
	public String getTranslate() {
		return translate;
	}
	public void setTranslate(String translate) {
		this.translate = translate;
	}

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "senseGroup")
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
		SenseGroup other = (SenseGroup) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return name;
	}
	
	

	
    
}
