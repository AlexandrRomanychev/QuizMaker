package game.quiz.hundredtoone.entity;

import javax.persistence.*;
import java.util.List;

/**
 * TODO Class Description
 *
 * @author Alexandr Romanychev
 * @since 02.03.2023
 */
@Entity
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String text;

	@OneToMany(mappedBy = "id")
	private List<Answer> answerList;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<Answer> getAnswerList() {
		return answerList;
	}

	public void setAnswerList(List<Answer> answerList) {
		this.answerList = answerList;
	}
}
