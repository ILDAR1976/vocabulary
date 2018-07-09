package iha.education.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import iha.education.entity.SenseGroup;

import java.util.List;


@Transactional(propagation = Propagation.MANDATORY)
public interface SenseGroupRepository extends CrudRepository<SenseGroup, Long> {

    List<SenseGroup> findAll();

}
