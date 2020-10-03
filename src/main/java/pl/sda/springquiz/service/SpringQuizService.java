package pl.sda.springquiz.service;

import pl.sda.entity.Question;

import java.util.List;
import java.util.Map;

public interface SpringQuizService {
    List<Question> getQuiz(long id);
    void completeQuiz(Map<Question, Integer> quizAnswers, long quizId);

    boolean transferPoints(long questionSourceId, long questionTargetId, int points) throws Exception;


}
