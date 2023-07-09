package game.quiz.hundredtoone.controller;

import game.quiz.hundredtoone.pojo.HundredToOneGamePojo;
import game.quiz.hundredtoone.pojo.QuestionPojo;
import game.quiz.hundredtoone.entity.Answer;
import game.quiz.hundredtoone.entity.HundredToOneGame;
import game.quiz.hundredtoone.entity.Question;
import game.quiz.hundredtoone.repository.AnswerRepository;
import game.quiz.hundredtoone.repository.HundredToOneRepository;
import game.quiz.hundredtoone.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
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

	@Autowired
	public HundredToOneController(
		QuestionRepository questionRepository,
		AnswerRepository answerRepository,
		HundredToOneRepository hundredToOneRepository
	) {
		this.questionRepository = questionRepository;
		this.answerRepository = answerRepository;
		this.hundredToOneRepository = hundredToOneRepository;
	}

	/**
	 * Получить стартовую страницу игры
	 */
	 @GetMapping("/")
	 public String getStartPage() {
		answerRepository.uncheckAnswers();
		 return "hundred_to_one/start_page";
	 }

	 @GetMapping("/game/{gameId}/{currentQuestion}/showAnswer/{id}")
	 public String showAnswer(
		 @PathVariable(value = "id") long id,
		 @PathVariable(value = "gameId") long gameId,
		 @PathVariable(value = "currentQuestion") int currentQuestion
	 ) {
		answerRepository.findById(id).ifPresent(answer -> {
			answer.setShowed(true);
			answerRepository.save(answer);
		});
		 return "redirect:/game/" + gameId + "/question/" + currentQuestion;
	 }

	 @GetMapping("/addNewGame")
	 public String addNewGame(Model model) {
		List<Question> allQuestions = questionRepository.findAll();
		List<QuestionPojo> questionPojoList = new ArrayList<>();
		allQuestions.forEach(question -> questionPojoList.add(new QuestionPojo(question)));
		HundredToOneGamePojo hundredToOneGamePojo = new HundredToOneGamePojo();
		hundredToOneGamePojo.setQuestionPojoList(questionPojoList);
		model.addAttribute("game", hundredToOneGamePojo);
		model.addAttribute("allPojoQuestions", questionPojoList);
		return "hundred_to_one/game_info";
	 }

	 @PostMapping("/saveGame")
	 public String saveGAme(@ModelAttribute("game") HundredToOneGamePojo hundredToOneGamePojo) {
		answerRepository.uncheckAnswers();
		List<Question> questionForGame = questionRepository.findAllById(
			hundredToOneGamePojo.getQuestionPojoList().stream()
				.filter(QuestionPojo::getChecked)
				.map(QuestionPojo::getId).collect(Collectors.toSet())
		);
		HundredToOneGame hundredToOneGame = new HundredToOneGame();
		hundredToOneGame.setCaption(hundredToOneGamePojo.getCaption());
		hundredToOneGame.setQuestionList(questionForGame);
		hundredToOneRepository.saveAndFlush(hundredToOneGame);
		return "redirect:/";
	 }

	 @GetMapping("game/{gameId}/question/{questionNumber}")
	 public String getQuestion(
		 @PathVariable(name = "gameId") long gameId,
		 @PathVariable(name = "questionNumber") int questionNumber,
		 Model model
	 ) {
		HundredToOneGame hundredToOneGame = hundredToOneRepository.getById(gameId);
		Question question = hundredToOneGame.getQuestionList().stream().sorted(Comparator.comparing(Question::getId)).collect(Collectors.toList()).get(questionNumber);
		model.addAttribute("question", question);
		model.addAttribute("answers", question.getAnswerList().stream().sorted(Comparator.comparing(Answer::getAnswerOrder)).collect(Collectors.toList()));
		model.addAttribute("gameId", hundredToOneGame.getId());
		model.addAttribute("currentQuestion", questionNumber);
		model.addAttribute("hasNextQuestion", questionNumber < hundredToOneGame.getQuestionList().size() - 1);
		return "hundred_to_one/game";
	 }

	 @GetMapping("/game/{gameId}/delete")
	 public String deleteGame(@PathVariable(name = "gameId") long gameId) {
		hundredToOneRepository.deleteById(gameId);
		return "redirect:/";
	 }

	@GetMapping("/game/{gameId}/edit")
	public String editGame(@PathVariable(name = "gameId") long gameId, Model model) {
		hundredToOneRepository.findById(gameId).ifPresent(hundredToOneGame -> {
			Set<Long> gameQuestionIds = hundredToOneGame.getQuestionList().stream().map(Question::getId).collect(Collectors.toSet());
			List<Question> allQuestions = questionRepository.findAll();
			List<QuestionPojo> questionPojoList = new ArrayList<>();
			allQuestions.forEach(question -> questionPojoList.add(new QuestionPojo(question)));
			questionPojoList.forEach(questionPojo -> {
				if (gameQuestionIds.contains(questionPojo.getId())) {
					questionPojo.setChecked(true);
				}
			});
			HundredToOneGamePojo hundredToOneGamePojo = new HundredToOneGamePojo(hundredToOneGame);
			model.addAttribute("game", hundredToOneGamePojo);
			model.addAttribute("allPojoQuestions", questionPojoList);
		});
		return "hundred_to_one/game_info";
	}

	 @GetMapping("/showAllGames")
	 public String getAllGames(Model model) {
		 List<HundredToOneGame> hundredToOneGames = hundredToOneRepository.findAll();
		 model.addAttribute("games", hundredToOneGames);
		 return "hundred_to_one/all_games";
	 }

	 @GetMapping(value = "/test", produces = "application/json")
	 public ResponseEntity<String> testRequest() {
		 return new ResponseEntity<>("{'test':'test'}", HttpStatus.OK);
	 }
}
