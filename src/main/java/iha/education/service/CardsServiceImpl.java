package iha.education.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import iha.education.entity.Cards;
import iha.education.entity.PartSpeech;
import iha.education.entity.SenseGroup;
import iha.education.entity.SubGroup;
import iha.education.repository.CardsRepository;
import iha.education.repository.PartSpeechRepository;
import iha.education.ui.CardsController;
import javafx.collections.ObservableList;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;


@Service
@Repository
@Transactional
public class CardsServiceImpl implements CardsService {

	private Logger logger = LoggerFactory.getLogger(CardsController.class);
	
	public CardsServiceImpl(){
		logger.info("Repository running ...");
	}
	
    @Autowired
    private CardsRepository repository;

    @Override
    public Cards save(Cards cards) {
        return repository.save(cards);
    }

    @Override
    public List<Cards> findAll() {
        return repository.findAll();
    }

	@Override
	public List<Cards> findByWord(String word) {
		return repository.findByWord(word);
	}

	@Override 
	public List<Cards> findByThirdFilter(PartSpeech partSpeech, SenseGroup senseGroup, SubGroup subGroup){
		return repository.findByThirdFilter(partSpeech, senseGroup, subGroup);
	}

	@Override
	public List<Cards> findByFifthFilterLike(PartSpeech partSpeech, SenseGroup senseGroup, SubGroup subGroup, String word,
			String translate) {
		return repository.findByFifthFilterLike(partSpeech, senseGroup, subGroup, word, translate);
	}

	@Override
	public List<Cards> findByVariantOneFilterLike(PartSpeech partSpeech, String word, String translate) {
		return repository.findByVariantOneFilterLike(partSpeech, word, translate);
	}

	@Override
	public List<Cards> findByVariantTwoFilterLike(SenseGroup senseGroup, String word, String translate) {
		return repository.findByVariantTwoFilterLike(senseGroup, word, translate);
	}

	@Override
	public List<Cards> findByVariantThreeFilterLike(SubGroup subGroup, String word, String translate) {
		return repository.findByVariantThreeFilterLike(subGroup, word, translate);
	}

	@Override
	public List<Cards> findByVariantFourFilterLike(PartSpeech partSpeech, SenseGroup senseGroup, String word,
			String translate) {
		return repository.findByVariantFourFilterLike(partSpeech, senseGroup, word, translate);
	}

	@Override
	public List<Cards> findByVariantFiveFilterLike(SenseGroup senseGroup, SubGroup subGroup, String word,
			String translate) {
		return repository.findByVariantFiveFilterLike(senseGroup, subGroup, word, translate);
	}

	@Override
	public List<Cards> findByVariantSixFilterLike(PartSpeech partSpeech, SubGroup subGroup, String word,
			String translate) {
		return repository.findByVariantSixFilterLike(partSpeech, subGroup, word, translate);
	}
	
	
}
