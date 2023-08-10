package ru.skypro.exam.Repository;

import org.springframework.stereotype.Repository;
import ru.skypro.exam.exceptions.QuestionAlreadyExistsException;
import ru.skypro.exam.exceptions.QuestionNotExistsException;
import ru.skypro.exam.model.Question;

import java.util.*;
import java.util.stream.Collectors;

@Repository("javaQuestionRepository")
public class JavaQuestionRepository implements QuestionRepository{
    private final Map<String, String> questions;
    public JavaQuestionRepository() {
        this.questions = new HashMap<>();
    }
    @Override
    public Question addQuestion(String question, String answer) throws QuestionAlreadyExistsException {
        if (questions.containsKey(question)) {
            throw new QuestionAlreadyExistsException();
        }
        questions.put(question, answer);
        return new Question(question, answer);
    }
    @Override
    public Question addQuestion(Question question) throws QuestionAlreadyExistsException {
        if (questions.containsKey(question.getQuestion())) {
            throw new QuestionAlreadyExistsException();
        }
        questions.put(question.getQuestion(), question.getAnswer());
        return new Question(question.getQuestion(), question.getAnswer());
    }

    @Override
    public Question removeQuestion(Question question) throws QuestionNotExistsException {
        Question foundQuestion = findQuestion(question.getQuestion());
        if (foundQuestion == null) {
            throw new QuestionNotExistsException();
        }
        questions.remove(question.getQuestion());
        return foundQuestion;
    }
    @Override
    public Question findQuestion(String question) {
        if (questions.containsKey(question)) {
            String answer = questions.get(question);
            return new Question(question, answer);
        } else {
            return null;
        }
    }
    @Override
    public Collection<Question> getAllQuestions() {
        return questions.entrySet().stream()
                .map(entry -> new Question(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
    @Override
    public Question getRandomQuestion() {
        Collection<Question> allQuestions = getAllQuestions();
        return allQuestions.stream()
                .skip(new Random().nextInt(allQuestions.size()))
                .findFirst()
                .orElse(null);
    }
}
