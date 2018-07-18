package iha.education.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iha.education.entity.SubGroup;
import iha.education.repository.SubGroupRepository;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class SubGroupServiceImpl implements SubGroupService {

    @Autowired
    private SubGroupRepository repository;

    @Override
    public SubGroup save(SubGroup SubGroup) {
        return repository.save(SubGroup);
    }

    @Override
    public List<SubGroup> findAll() {
        return repository.findAll();
    }

	@Override
	public SubGroup findById(int id) {
		return repository.findById(Long.parseLong(Integer.toString(id))).get();
	}

	@Override
	public SubGroup findById(Long id) {
		return repository.findById(id).get();
	}

	@Override
	public SubGroup findByName(String name) {
		return repository.findByName(name);
	}

}
