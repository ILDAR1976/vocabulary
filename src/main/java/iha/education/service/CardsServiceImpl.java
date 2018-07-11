package iha.education.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import iha.education.entity.Cards;
import iha.education.entity.PartSpeech;
import iha.education.repository.CardsRepository;
import iha.education.repository.PartSpeechRepository;
import javafx.collections.ObservableList;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;


@Service
@Repository
@Transactional
public class CardsServiceImpl implements CardsService {

    @Autowired
    private CardsRepository repository;

    @Autowired
    private PartSpeechRepository partSpeechRepository;
 
    @Override
    public Cards save(Cards cards) {
        return repository.save(cards);
    }

    @Override
    public List<Cards> findAll() {
        return repository.findAll();
    }

	@Override
	public List<Cards> findBySensGroup() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PartSpeech> findAllFromPartSpeech() {
		
		return repository.findAllFromPartSpeech();
	}

	
}
