package game.quiz.hundredtoone.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Ответ на вопрос для 100 к одному
 *
 * @author Alexandr Romanychev
 * @since 02.03.2023
 */
@Setter
@Getter
@Entity
@Table(name = "answer")
public class Answer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private boolean showed;
	private String text;
}
