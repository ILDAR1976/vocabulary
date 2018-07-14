package iha.education.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iha.education.entity.SenseGroup;
import iha.education.repository.SenseGroupRepository;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class SenseGroupServiceImpl implements SenseGroupService {

    @Autowired
    private SenseGroupRepository repository;

    @Override
    public SenseGroup save(SenseGroup senseGroup) {
        return repository.save(senseGroup);
    }

    @Override
    public List<SenseGroup> findAll() {
        return repository.findAll();
    }

	@Override
	public SenseGroup findById(int id) {
		return repository.findOne((long)id);
	}

	@Override
	public SenseGroup findById(Long id) {
		return repository.findOne(id);
	}

	@Override
	public SenseGroup findByName(String name) {
		return repository.findByName(name);
	}

}
