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
    List<Cards> findByWordLike(String word);
    List<Cards> findByTranslateLike(String translate);
    List<Cards> findBySecondFilterLike(String word, String translate);
    List<Cards> findByThirdFilter(PartSpeech partSpeech, SenseGroup senseGroup, SubGroup subGroup);
    List<Cards> findByFifthFilterLike(PartSpeech partSpeech, SenseGroup senseGroup, SubGroup subGroup, String word, String translate);
    List<Cards> findByVariantOneFilterLike(PartSpeech partSpeech, String word, String translate);
    List<Cards> findByVariantTwoFilterLike(SenseGroup senseGroup, String word, String translate);
    List<Cards> findByVariantThreeFilterLike(SubGroup subGroup, String word, String translate);
    List<Cards> findByVariantFourFilterLike(PartSpeech partSpeech, SenseGroup senseGroup, String word, String translate);
    List<Cards> findByVariantFiveFilterLike(SenseGroup senseGroup, SubGroup subGroup, String word, String translate);
    List<Cards> findByVariantSixFilterLike(PartSpeech partSpeech, SubGroup subGroup, String word, String translate);

}
