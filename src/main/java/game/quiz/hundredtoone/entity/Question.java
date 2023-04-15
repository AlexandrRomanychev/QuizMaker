package game.quiz.hundredtoone.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Вопрос
 *
 * @author Alexandr Romanychev
 * @since 02.03.2023
 */
@Setter
@Getter
@Entity
@Table(name = "question")
public class Question {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String text;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "answer_id")
	private List<Answer> answerList;

	@ManyToMany(mappedBy = "questionList")
	private Set<HundredToOneGame> hundredToOneGameSet;
}
