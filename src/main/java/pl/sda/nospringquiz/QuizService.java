package pl.sda.nospringquiz;

import pl.sda.entity.Question;

import java.util.List;

public interface QuizService {
    List<Question> getQuiz(long id);
}
