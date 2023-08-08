package ru.skypro.exam.Repository;

import org.springframework.stereotype.Repository;
import ru.skypro.exam.exceptions.AnswerAlreadyExistsException;
import ru.skypro.exam.exceptions.QuestionAlreadyExistsException;
import ru.skypro.exam.exceptions.QuestionNotExistsException;
import ru.skypro.exam.model.Question;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
@Repository("javaQuestionRepository")
public class JavaQuestionRepository implements QuestionRepository{
    private final Set<Question> questions;

    public JavaQuestionRepository(Set<Question> questions) {
        this.questions = questions;
    }

    @Override
    public Question addQuestion(String question, String answer) throws QuestionAlreadyExistsException, AnswerAlreadyExistsException {
        boolean questionExists = questions.stream().anyMatch(existingQuestion -> existingQuestion.getQuestion().equals(question));
        boolean answerExists = questions.stream().anyMatch(existingQuestion -> existingQuestion.getAnswer().equals(answer));

        if (questionExists || answerExists) {
            if (questionExists) {
                throw new QuestionAlreadyExistsException();
            } else {
                throw new AnswerAlreadyExistsException();
            }
        }

        Question newQuestion = new Question(question, answer);
        questions.add(newQuestion);
        return newQuestion;
    }

    @Override
    public Question removeQuestion(String questionText) throws QuestionNotExistsException {
        Optional<Question> removedQuestion = questions.stream()
                .filter(existingQuestion -> existingQuestion.getQuestion().equals(questionText))
                .findFirst();
        if (removedQuestion.isPresent()) {
            questions.remove(removedQuestion.get());
            return removedQuestion.get();
        } else {
            throw new QuestionNotExistsException();
        }
    }

    @Override
    public Collection<Question> getAllQuestions() {
        return questions;
    }
}
