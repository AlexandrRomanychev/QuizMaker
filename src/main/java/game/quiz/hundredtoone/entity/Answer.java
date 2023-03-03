package game.quiz.hundredtoone.entity;

import javax.persistence.*;

/**
 * Ответ на вопрос для 100 к одному
 *
 * @author Alexandr Romanychev
 * @since 02.03.2023
 */
@javax.persistence.Entity
public class Answer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private boolean showed;
	private String text;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isShowed() {
		return showed;
	}

	public void setShowed(boolean showed) {
		this.showed = showed;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
