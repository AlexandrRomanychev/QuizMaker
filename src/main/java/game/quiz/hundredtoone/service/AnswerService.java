package game.quiz.hundredtoone.service;

import game.quiz.hundredtoone.entity.Answer;
import game.quiz.hundredtoone.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для работы с ответами
 *
 * @author Alexandr Romanychev
 * @since 03.03.2023
 */
@Service
public class AnswerService {

	private final AnswerRepository answerRepository;

	@Autowired
	public AnswerService(AnswerRepository answerRepository) {
		this.answerRepository = answerRepository;
	}

	/**
	 * Сделать все показанные ответы скрытыми
	 */
	public void uncheckAnswers() {
		Answer answer = new Answer();
		answer.setShowed(true);
		Example<Answer> answerExample = Example.of(answer);
		List<Answer> showedAnswers = answerRepository.findAll(answerExample);
		showedAnswers.forEach(showedAnswer -> showedAnswer.setShowed(false));
		answerRepository.saveAll(showedAnswers);
	}
}
