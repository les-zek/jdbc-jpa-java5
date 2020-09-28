package pl.sda.springquiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.entity.Answer;
import pl.sda.entity.CompletedQuiz;
import pl.sda.entity.Question;
import pl.sda.entity.Quiz;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SpringQuizServiceJpa implements SpringQuizService {
    private final SpringQuizRepository springQuizRepository;
    private final CompletedQuizRepository completedQuizRepository;

    @Autowired
    public SpringQuizServiceJpa(SpringQuizRepository springQuizRepository, CompletedQuizRepository completedQuizRepository) {
        this.springQuizRepository = springQuizRepository;
        this.completedQuizRepository = completedQuizRepository;
    }

    @Override
    public List<Question> getQuiz(long id) {
        Optional<Quiz> quiz = springQuizRepository.findById(id);
        if (quiz.isPresent()) {
            return new ArrayList<>(quiz.get().getQuestions());
        }
        return Collections.emptyList();
    }

    @Override
    public void completeQuiz(Map<Question, Integer> quizAnswers, long quizId) {
        Optional<Quiz> quiz = springQuizRepository.findById(quizId);
        Set<Answer> answers = quizAnswers
                .entrySet()
                .stream()
                .map(entry -> Answer.builder().optionNumber(entry.getValue()).question(entry.getKey()).build())
                .collect(Collectors.toSet());
        quiz.flatMap(q -> Optional.of(
                CompletedQuiz.builder()
                        .quiz(q)
                        .answers(answers)
                        .build()
                )
        ).ifPresent(completedQuizRepository::save);
    }
}
