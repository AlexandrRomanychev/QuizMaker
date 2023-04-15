package game.quiz.hundredtoone.controller;

import game.quiz.hundredtoone.entity.Answer;
import game.quiz.hundredtoone.entity.Question;
import game.quiz.hundredtoone.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

/**
 * Контроллер для обработки вопросов
 *
 * @author Alexandr Romanychev
 * @since 14.04.2023
 */
@Controller
public class QuestionController {

	private final QuestionRepository questionRepository;

	@Autowired
	public QuestionController(QuestionRepository questionRepository) {
		this.questionRepository = questionRepository;
	}
	/**
	 * Добавить новый вопрос
	 */
	@GetMapping("/addNewQuestion")
	public String addNewQuestion(Model model) {
		Question question = new Question();
		question.setAnswerList(Arrays.asList(new Answer(), new Answer(),new Answer(),new Answer(),new Answer(),new Answer()));
		model.addAttribute("question", question);
		return "/hundred_to_one/new_question";
	}

	@GetMapping("/showAllQuestions")
	public String showAllQuestions(Model model) {
		List<Question> allQuestions = questionRepository.findAll();
		model.addAttribute("questions", allQuestions);
		return "/hundred_to_one/all_questions";
	}

}
