package game.quiz.hundredtoone.repository;

import game.quiz.hundredtoone.entity.HundredToOneGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для игры 100 на 1
 *
 * @author Alexandr Romanychev
 * @since 03.03.2023
 */
@Repository
public interface HundredToOneRepository extends JpaRepository<HundredToOneGame, Long> {
}
