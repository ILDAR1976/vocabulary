package iha.education.service;

import iha.education.entity.Cards;
import javafx.collections.ObservableList;

import java.util.List;


public interface CardsService {

    Cards save(Cards cards);
    List<Cards> findAll();
    List<Cards> findBySensGroup();
}
