package iha.education.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import iha.education.entity.PartSpeech;

import java.util.List;


@Transactional(propagation = Propagation.MANDATORY)
public interface PartSpeechRepository extends CrudRepository<PartSpeech, Long> {

    List<PartSpeech> findAll();
 
}
