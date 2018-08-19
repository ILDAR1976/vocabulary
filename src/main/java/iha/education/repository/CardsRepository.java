package iha.education.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import iha.education.entity.Cards;
import iha.education.entity.PartSpeech;
import iha.education.entity.SenseGroup;
import iha.education.entity.SubGroup;

import java.util.List;

import javax.persistence.NamedQuery;


@Transactional(propagation = Propagation.MANDATORY)
public interface CardsRepository extends CrudRepository<Cards, Long> {

	List<Cards> findAll();
	@Query(nativeQuery=true)
	List<Cards> findByWord(@Param("word")String word);
	@Query(nativeQuery=true)
	List<Cards> findByThirdFilter(@Param("partSpeech") PartSpeech partSpeech,
								  @Param("senseGroup") SenseGroup senseGroup,
			                      @Param("subGroup") SubGroup subGroup);
	@Query(nativeQuery=true)
	List<Cards> findByFifthFilterLike(@Param("partSpeech") PartSpeech partSpeech,
								      @Param("senseGroup") SenseGroup senseGroup,
			                          @Param("subGroup") SubGroup subGroup,
			                          @Param("word")String word,
			                          @Param("translate")String translate);
	
	@Query("select c from Cards c where" 
	     + " c.partSpeech=:partSpeech and"
		 + " c.word like :word and"  
		 + " c.translate like :translate")
	List<Cards> findByVariantOneFilterLike(@Param("partSpeech") PartSpeech partSpeech,
			                               @Param("word")String word,
			                               @Param("translate")String translate);

	@Query("select c from Cards c where" 
			 + " c.senseGroup=:senseGroup and"  
			 + " c.word like :word and"  
			 + " c.translate like :translate")
	List<Cards> findByVariantTwoFilterLike(@Param("senseGroup") SenseGroup SenseGroup,
			                               @Param("word")String word,
			                               @Param("translate")String translate);

	@Query("select c from Cards c where" 
			 + " c.subGroup=:subGroup and"  
			 + " c.word like :word and"  
			 + " c.translate like :translate")
	List<Cards> findByVariantThreeFilterLike(@Param("subGroup") SubGroup subGroup,
			                                @Param("word")String word,
			                                @Param("translate")String translate);

	@Query("select c from Cards c where" 
		     + " c.partSpeech=:partSpeech and"
			 + " c.senseGroup=:senseGroup and"  
			 + " c.word like :word and"  
			 + " c.translate like :translate")
	List<Cards> findByVariantFourFilterLike(@Param("partSpeech") PartSpeech partSpeech,
											@Param("senseGroup") SenseGroup senseGroup,
			                                @Param("word")String word,
			                                @Param("translate")String translate);

	@Query("select c from Cards c where" 
			 + " c.senseGroup=:senseGroup and"  
			 + " c.subGroup=:subGroup and"  
			 + " c.word like :word and"  
			 + " c.translate like :translate")
	List<Cards> findByVariantFiveFilterLike(@Param("senseGroup") SenseGroup senseGroup,
											@Param("subGroup") SubGroup subGroup,
			                                @Param("word")String word,
			                                @Param("translate")String translate);

	@Query("select c from Cards c where" 
		     + " c.partSpeech=:partSpeech and"
			 + " c.subGroup=:subGroup and"  
			 + " c.word like :word and"  
			 + " c.translate like :translate")
	List<Cards> findByVariantSixFilterLike(@Param("partSpeech") PartSpeech partSpeech,
										   @Param("subGroup") SubGroup subGroup,
			                               @Param("word")String word,
			                               @Param("translate")String translate);

/*	@Query("insert into Cards (id, partSpeech, senseGroup, subGroup, word, translate, example ) values"
			 + " ( :id , :partSpeech, :senseGroup, "
			 + " :subGroup, :word, :translate, :example )")   
	void saveCard(  
					@Param("id") Long id,
					@Param("partSpeech") PartSpeech partSpeech,
					@Param("senseGroup") SenseGroup senseGroup,
					@Param("subGroup") SubGroup subGroup,
					@Param("word") String word,
					@Param("translate") String translate,
					@Param("example") String example
				 );
	
	@Query("insert into Cards(id) VALUES (:id)")   
	void saveCard(  
					@Param("id") Long id);
					
 */
	
}

