package game.quiz.hundredtoone.repository;

import game.quiz.hundredtoone.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Репозиторий для работы с ответами
 *
 * @author Alexandr Romanychev
 * @since 03.03.2023
 */
@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

	@Modifying
	@Transactional
	@Query(value = "UPDATE answer SET showed = false WHERE showed = true", nativeQuery = true)
	void uncheckAnswers();
}
