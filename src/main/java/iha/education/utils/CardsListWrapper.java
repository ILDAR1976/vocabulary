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
public class CardsListWrapper implements Serializable {

	private List<PartSpeech> partSpeech;
	private List<SenseGroup> senseGroup;
	private List<SubGroup> subGroup;
	

	public CardsListWrapper() {

	}

	public CardsListWrapper(List<PartSpeech> partSpeech, List<SenseGroup> senseGroup,
			List<SubGroup> subGroup) {
		
	}


	@XmlElement(name = "partSpeech")
	public List<PartSpeech> getPartSpeech() {
		return partSpeech;
	}

	public void setPartSpeech(List<PartSpeech> partSpeech) {
		this.partSpeech = partSpeech;
	}

	@XmlElement(name = "senseGroup")
	public List<SenseGroup> getSenseGroup() {
		return senseGroup;
	}

	public void setSenseGroup(List<SenseGroup> senseGroup) {
		this.senseGroup = senseGroup;
	}

	@XmlElement(name = "subGroup")
	public List<SubGroup> getSubGroup() {
		return subGroup;
	}

	public void setSubGroup(List<SubGroup> subGroup) {
		this.subGroup = subGroup;
	}

	
}
