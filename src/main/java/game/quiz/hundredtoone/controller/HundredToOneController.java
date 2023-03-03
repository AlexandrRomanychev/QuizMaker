package game.quiz.hundredtoone.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import game.quiz.hundredtoone.entity.Answer;
import game.quiz.hundredtoone.entity.Question;
import game.quiz.hundredtoone.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;

/**
 * Контроллер для игры 100 к одному
 *
 * @author Alexandr Romanychev
 * @since 02.03.2023
 */
@Controller
public class HundredToOneController {

	@Autowired
	private QuestionRepository questionRepository;

	 @GetMapping("/")
	 public String getStartPage(Model model) {
		 List<Question> questions = questionRepository.findAll();
		 model.addAttribute("questions", questions);
//		 model.addAttribute("question", "First question");
//		 model.addAttribute("questionsCount", 0);
//		 model.addAttribute("answers", answers);
		 return "startpage";
	 }

	 @GetMapping("/addNewQuestion")
	 public String addNewQuestion(Model model) {
		 model.addAttribute("question", new Question());
		 return "newquestion";
	 }

	 @PostMapping("/saveNewQuestion")
	 public String saveNewQuestion(@ModelAttribute("question") Question question) {
		questionRepository.save(question);
		return "redirect:/";
	 }

	 @GetMapping("/showAnswer/{id}")
	 public String showAnswer(@PathVariable(value = "id") long id) {
//		 answers.stream().filter(answer -> answer.getId() == id).findFirst().ifPresent(answer -> answer.setShowed(true));
		 return "redirect:/";
	 }
}
