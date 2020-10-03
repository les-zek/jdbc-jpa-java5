package pl.sda.springquiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sda.entity.Answer;
import pl.sda.entity.CompletedQuiz;
import pl.sda.entity.Question;
import pl.sda.entity.Quiz;
import pl.sda.springquiz.repository.CompletedQuizRepository;
import pl.sda.springquiz.repository.SpringQuestionRepository;
import pl.sda.springquiz.repository.SpringQuizRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SpringQuizServiceJpa implements SpringQuizService {
    private final SpringQuizRepository springQuizRepository;
    private final CompletedQuizRepository completedQuizRepository;
    private final SpringQuestionRepository questionRepository;

    @Autowired
    public SpringQuizServiceJpa(SpringQuizRepository springQuizRepository, CompletedQuizRepository completedQuizRepository, SpringQuestionRepository questionRepository) {
        this.springQuizRepository = springQuizRepository;
        this.completedQuizRepository = completedQuizRepository;
        this.questionRepository = questionRepository;
    }


    @Override
    public List<Question> getQuiz(long id) {
        Optional<Quiz> quiz = springQuizRepository.findById(id);
        if (quiz.isPresent()) {
            return new ArrayList<>(quiz.get().getQuestions());
        }
        return Collections.emptyList();
    }

    // @Transactional działa tylko dla metod publicznych
    // rollback robimy przez zgłoszenie wyjątku
    @Override
    @Transactional
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean transferPoints(long questionSourceId, long questionTargetId, int points) throws Exception {
        Optional<Question> source = questionRepository.findById(questionSourceId);
        Optional<Question> target = questionRepository.findById(questionTargetId);
        if (source.isPresent() && target.isPresent()) {
            Question sourceQuestion = source.get();
            Question targetQuestion = target.get();
            sourceQuestion.setPoints(sourceQuestion.getPoints() - points);
            targetQuestion.setPoints(targetQuestion.getPoints() + points);

            if (sourceQuestion.getPoints() < 0) {
                throw new Exception();  // rzucenie wyjątku powoduje rollback
            }
            return true;  // brak wystąpienia wyjątku powoduje automatyczny commi
        }
        return false;
    }
}
