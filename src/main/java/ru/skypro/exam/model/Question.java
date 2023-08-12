package ru.skypro.exam.model;

import java.util.Objects;
import java.util.UUID;

public class Question {
    private String question;
    private String answer;
    private final String questionId;

    public Question(String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.questionId = generateId();
    }
    private String generateId() {
        return UUID.randomUUID().toString();
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getId() {
        return questionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;
        Question question1 = (Question) o;
        return Objects.equals(question, question1.question) && Objects.equals(answer, question1.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, answer);
    }

    @Override
    public String toString() {
        return question + ": " + answer;
    }
}
