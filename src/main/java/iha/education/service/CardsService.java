package iha.education.service;

import iha.education.entity.Cards;
import iha.education.entity.PartSpeech;
import iha.education.entity.SenseGroup;
import iha.education.entity.SubGroup;
import javafx.collections.ObservableList;

import java.util.List;


public interface CardsService {

    Cards save(Cards cards);
    List<Cards> findAll();
    List<Cards> findByWord(String word);
    List<Cards> findByThirdFilter(PartSpeech partSpeech, SenseGroup senseGroup, SubGroup subGroup);
}
