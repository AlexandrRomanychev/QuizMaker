package game.quiz.hundredtoone.repository;

import game.quiz.hundredtoone.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с ответами
 *
 * @author Alexandr Romanychev
 * @since 03.03.2023
 */
@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

}
