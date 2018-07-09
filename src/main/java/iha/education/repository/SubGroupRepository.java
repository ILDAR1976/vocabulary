package iha.education.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import iha.education.entity.SubGroup;

import java.util.List;


@Transactional(propagation = Propagation.MANDATORY)
public interface SubGroupRepository extends CrudRepository<SubGroup, Long> {

    List<SubGroup> findAll();

}
