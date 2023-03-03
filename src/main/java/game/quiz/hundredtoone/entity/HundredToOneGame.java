package game.quiz.hundredtoone.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Информация об игре 100 к 1
 *
 * @author Alexandr Romanychev
 * @since 03.03.2023
 */
@Setter
@Getter
@Entity
@Table(name = "hundred_to_one")
public class HundredToOneGame {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String caption;
	@ManyToMany
	@JoinTable(
		name = "hundredToOneQuestions",
		joinColumns = { @JoinColumn(name = "question_id") },
		inverseJoinColumns = { @JoinColumn(name = "hundred_to_one_id") })
	private List<Question> questionList;
}
