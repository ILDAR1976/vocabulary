package iha.education.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import iha.education.entity.Cards;


import java.util.List;


@Transactional(propagation = Propagation.MANDATORY)
public interface CardsRepository extends CrudRepository<Cards, Long> {

	List<Cards> findAll();
	@Query(nativeQuery=true)
	List<Cards> findByWord(@Param("word")String word);
	
}
