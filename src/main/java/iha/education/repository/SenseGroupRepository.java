package iha.education.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import iha.education.entity.SenseGroup;

import java.util.List;


@Transactional(propagation = Propagation.MANDATORY)
public interface SenseGroupRepository extends CrudRepository<SenseGroup, Long> {

    List<SenseGroup> findAll();
    @Query(nativeQuery=true)
    SenseGroup findByName(@Param("name")String name);
    SenseGroup findTop1By();

}
