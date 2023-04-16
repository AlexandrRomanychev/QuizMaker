package game.quiz.hundredtoone.controller;

import game.quiz.hundredtoone.entity.Answer;
import game.quiz.hundredtoone.entity.Question;
import game.quiz.hundredtoone.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
	@GetMapping(value = "/addNewQuestion")
	public String addNewQuestion() {
		return "/hundred_to_one/new_question";
	}

	/**
	 * Добавить новый вопрос
	 */
	@PostMapping(value = "/addNewQuestion")
	public String showNewQuestion() {
		return "redirect:/";
	}

	@GetMapping("/showAllQuestions")
	public String showAllQuestions(Model model) {
		List<Question> allQuestions = questionRepository.findAll();
		model.addAttribute("questions", allQuestions);
		return "/hundred_to_one/all_questions";
	}

	@PostMapping(
		value = "/saveNewQuestion",
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<String> saveNewQuestion(@RequestBody Question question) {
		List<Answer> answers = question.getAnswerList().stream().filter(answer -> !answer.getText().isEmpty()).collect(Collectors.toList());
		//TODO подумать может можно избавиться от порядка
		AtomicInteger order = new AtomicInteger(1);
		answers.forEach(answer -> {answer.setAnswerOrder(order.get()); order.getAndIncrement();});
		question.setAnswerList(answers);
		questionRepository.save(question);
		return new ResponseEntity<>("ok", HttpStatus.OK);
	}
}
