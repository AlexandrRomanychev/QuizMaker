package game.quiz.hundredtoone.pojo;

import game.quiz.hundredtoone.entity.Question;
import lombok.NoArgsConstructor;

/**
 * TODO Class Description
 *
 * @author Alexandr Romanychev
 * @since 03.03.2023
 */
@NoArgsConstructor
public class QuestionPojo {

	private Long id;
	private String text;
	private boolean checked = false;

	public QuestionPojo(Question question) {
		this.id = question.getId();
		this.text = question.getText();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean getChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
