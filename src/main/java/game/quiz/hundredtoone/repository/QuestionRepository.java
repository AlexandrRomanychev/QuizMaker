package game.quiz.hundredtoone.repository;

import game.quiz.hundredtoone.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * TODO Class Description
 *
 * @author Alexandr Romanychev
 * @since 02.03.2023
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
