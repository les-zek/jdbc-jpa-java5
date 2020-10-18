package pl.sda.springquiz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.dto.QuestionDto;
import pl.sda.springquiz.repository.SpringQuestionRepository;

import java.util.Arrays;
import java.util.Optional;

@RestController
public class QuizRestController {
    public QuizRestController(SpringQuestionRepository repository) {
        this.repository = repository;
    }

    private final SpringQuestionRepository repository;

    @GetMapping("/questions/{id}")
    Optional<QuestionDto> findQuestion(@PathVariable long id) {
        return repository.findById(id)
                .flatMap(q -> Optional.of(new QuestionDto(q.getId(), q.getBody(),
                                Arrays.asList(
                                        q.getOptions().getOption1(),
                                        q.getOptions().getOption2(),
                                        q.getOptions().getOption3(),
                                        q.getOptions().getOption4()
                                )
                        )
                        )
                );
    }
}
