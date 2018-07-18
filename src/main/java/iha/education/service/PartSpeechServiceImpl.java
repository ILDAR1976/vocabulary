package iha.education.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iha.education.entity.PartSpeech;
import iha.education.repository.PartSpeechRepository;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class PartSpeechServiceImpl implements PartSpeechService {

    @Autowired
    private PartSpeechRepository repository;

    @Override
    public PartSpeech save(PartSpeech PartSpeech) {
        return repository.save(PartSpeech);
    }

    @Override
    public List<PartSpeech> findAll() {
        return repository.findAll();
    }

	
	@Override
	public PartSpeech findByName(String name) {
		return repository.findByName(name);
	}

	@Override
	public PartSpeech findById(int id) {
		return repository.findById(Long.parseLong(Integer.toString(id))).get();
	}

	@Override
	public PartSpeech findById(Long id) {
		return repository.findById(id).get();
	}

}
