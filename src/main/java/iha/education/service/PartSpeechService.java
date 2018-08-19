package iha.education.service;

import iha.education.entity.PartSpeech;

import java.util.List;


public interface PartSpeechService {

    PartSpeech save(PartSpeech PartSpeech);
    List<PartSpeech> findAll();
    PartSpeech findById(int id);
    PartSpeech findById(Long id);
    PartSpeech findByName(String name);
    PartSpeech findTop1By();
    

}
