package game.quiz.hundredtoone.pojo;

import game.quiz.hundredtoone.entity.HundredToOneGame;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO Class Description
 *
 * @author Alexandr Romanychev
 * @since 03.03.2023
 */
@Data
@Getter
@Setter
public class HundredToOneGamePojo {
	private String caption;
	private List<QuestionPojo> questionPojoList;

	public HundredToOneGamePojo() {
		this.questionPojoList = new ArrayList<>();
	}
	public HundredToOneGamePojo(HundredToOneGame hundredToOneGame) {
		this();
		this.caption = hundredToOneGame.getCaption();
		hundredToOneGame.getQuestionList().forEach(question -> questionPojoList.add(new QuestionPojo(question)));
	}
}
