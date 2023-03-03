package game.quiz.hundredtoone.controller;

import game.quiz.hundredtoone.pojo.HundredToOneGamePojo;
import game.quiz.hundredtoone.pojo.QuestionPojo;
import game.quiz.hundredtoone.entity.Answer;
import game.quiz.hundredtoone.entity.HundredToOneGame;
import game.quiz.hundredtoone.entity.Question;
import game.quiz.hundredtoone.repository.AnswerRepository;
import game.quiz.hundredtoone.repository.HundredToOneRepository;
import game.quiz.hundredtoone.repository.QuestionRepository;
import game.quiz.hundredtoone.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер для игры 100 к одному
 *
 * @author Alexandr Romanychev
 * @since 02.03.2023
 */
@Controller
public class HundredToOneController {

	private final QuestionRepository questionRepository;
	private final AnswerRepository answerRepository;
	private final HundredToOneRepository hundredToOneRepository;
	private final AnswerService answerService;

	@Autowired
	public HundredToOneController(
		QuestionRepository questionRepository,
		AnswerRepository answerRepository,
		HundredToOneRepository hundredToOneRepository,
		AnswerService answerService
	) {
		this.questionRepository = questionRepository;
		this.answerRepository = answerRepository;
		this.hundredToOneRepository = hundredToOneRepository;
		this.answerService = answerService;
	}

	 @GetMapping("/")
	 public String getStartPage(Model model) {
		answerService.uncheckAnswers();
		 List<Question> questions = questionRepository.findAll();
		 model.addAttribute("questions", questions);
		 return "startpage";
	 }

	 @GetMapping("/addNewQuestion")
	 public String addNewQuestion(Model model) {
		Question question = new Question();
		question.setAnswerList(Arrays.asList(new Answer(), new Answer(),new Answer(),new Answer(),new Answer(),new Answer()));
		model.addAttribute("question", question);
		return "newquestion";
	 }

	 @PostMapping("/saveNewQuestion")
	 public String saveNewQuestion(@ModelAttribute("question") Question question) {
		questionRepository.save(question);
		return "redirect:/";
	 }

	 @GetMapping("/game/{gameId}/{currentQuestion}/showAnswer/{id}")
	 public String showAnswer(
		 @PathVariable(value = "id") long id,
		 @PathVariable(value = "gameId") long gameId,
		 @PathVariable(value = "currentQuestion") int currentQuestion,
		 Model model
	 ) {
		answerRepository.findById(id).ifPresent(answer -> {
			answer.setShowed(true);
			answerRepository.save(answer);
		});
		 return "redirect:/game/" + gameId + "/question/" + currentQuestion;
	 }

	 @GetMapping("/allQuestions")
	 public String showAllQuestions(Model model) {
		List<Question> allQuestions = questionRepository.findAll();
		List<QuestionPojo> questionPojoList = new ArrayList<>();
		allQuestions.forEach(question -> questionPojoList.add(new QuestionPojo(question)));
		HundredToOneGamePojo hundredToOneGamePojo = new HundredToOneGamePojo();
		hundredToOneGamePojo.setQuestionPojoList(questionPojoList);
		model.addAttribute("game", hundredToOneGamePojo);
		model.addAttribute("allPojoQuestions", questionPojoList);
		return "allquestions";
	 }

	 @PostMapping("/createNewGame")
	 public String createNewGame(@ModelAttribute("game") HundredToOneGamePojo hundredToOneGamePojo, Model model) {
		answerService.uncheckAnswers();
		List<Question> questionForGame = questionRepository.findAllById(
			hundredToOneGamePojo.getQuestionPojoList().stream()
				.filter(QuestionPojo::getChecked)
				.map(QuestionPojo::getId).collect(Collectors.toSet())
		);
		HundredToOneGame hundredToOneGame = new HundredToOneGame();
		hundredToOneGame.setQuestionList(questionForGame);
		hundredToOneRepository.save(hundredToOneGame);
		return "redirect:/game/" + hundredToOneGame.getId() + "/question/0";
	 }

	 @GetMapping("game/{gameId}/question/{questionNumber}")
	 public String getQuestion(
		 @PathVariable(name = "gameId") long gameId,
		 @PathVariable(name = "questionNumber") int questionNumber,
		 Model model
	 ) {
		HundredToOneGame hundredToOneGame = hundredToOneRepository.getById(gameId);
		hundredToOneGame.getQuestionList().get(questionNumber);
		 model.addAttribute("question", hundredToOneGame.getQuestionList().get(questionNumber));
		 model.addAttribute("gameId", hundredToOneGame.getId());
		 model.addAttribute("currentQuestion", questionNumber);
		 model.addAttribute("hasNextQuestion", questionNumber < hundredToOneGame.getQuestionList().size() - 1);
		return "hundredtoone";
	 }
}
