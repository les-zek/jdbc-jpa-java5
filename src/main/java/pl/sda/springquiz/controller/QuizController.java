package pl.sda.springquiz.controller;

import pl.sda.entity.Question;
import pl.sda.springquiz.service.SpringQuizService;

import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

public class QuizController {
    Map<Question, Integer> answers = new HashMap<>();
    private final SpringQuizService quizService;
    ListIterator<Question> questions;
    Question currentQuestion;
    private long quizId;

    public QuizController(SpringQuizService quizService, long quizId) {
        this.quizService = quizService;
        this.quizId = quizId;
        questions = quizService.getQuiz(quizId).listIterator();
    }

    public Question next(){
        if(questions.hasNext()){
            currentQuestion = questions.next();
        }
        return currentQuestion;
    }
    public Question previous(){
        if(questions.hasPrevious()){
            currentQuestion = questions.previous();
        }
        return currentQuestion;
    }

    public void saveAnswer(Question question, int answer){
        answers.put(question, answer);
    }

    public void completeQuiz(){
        quizService.completeQuiz(answers, quizId);
    }

    public int summary(){
       return answers.entrySet()
                .stream()
                .filter(entry -> entry.getKey().getValidOption() == entry.getValue())
                .mapToInt(entry -> entry.getKey().getPoints())
                .sum();
    }
}
