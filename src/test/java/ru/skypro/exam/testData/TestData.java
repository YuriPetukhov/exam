package ru.skypro.exam.testData;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;
import ru.skypro.exam.model.Question;

import java.util.Arrays;
import java.util.List;

@Component
public class TestData {

    public static Question randomTestData() {
        Faker faker = new Faker();

        List<String> questions = Arrays.asList(
                "Что такое Java?",
                "Кто является создателем Java?",
                "Как называется процесс выполнения кода Java?",
                "Какие операторы сравнения используются в Java?",
                "Выберите правильный способ объявления массива в Java.",
                "Какая конструкция используется для создания пользовательских исключений?",
                "Какой пакет предоставляет классы для работы с датами и временем?",
                "Что такое инкапсуляция?",
                "Что такое наследование?",
                "Какие существуют модификаторы доступа в Java?",
                "Что такое абстракция?",
                "Что такое полиморфизм?",
                "Что такое интерфейс?",
                "Какой специфичный для Java ключевой оператор используется для обработки исключений?"
        );
        List<String> answers = Arrays.asList(
                "Объектно-ориентированный язык программирования",
                "Джеймс Гослинг",
                "Исполнение виртуальной машиной Java (JVM)",
                "<, >, <=, >=, ==, !=",
                "int[] nums;",
                "Объявление своего класса исключений, наследуясь от класса Exception",
                "java.time",
                "Сокрытие деталей реализации и предоставление только обязательного функционала",
                "Процесс, когда один класс наследует свойства и методы другого класса",
                "public, protected, private, default",
                "Способ представления реальных объектов и проблем с помощью обобщенных концепций",
                "Способность одного объекта принимать различные формы в зависимости от контекста",
                "Контракт или набор методов, которые класс должен реализовать",
                "try-catch"
        );

        int randomIndex = faker.random().nextInt(questions.size());
        String question = questions.get(randomIndex);
        String answer = answers.get(randomIndex);

        return new Question(question, answer);
    }
    public static Question randomTestDataWithAnswer(){
        Question question = randomTestData();
        question.setAnswer(question.getAnswer());
        return question;
    }

    public static Question randomNonExistingQuestion(){
        Faker faker = new Faker();
        String questionText = "Не существующий вопрос: " + faker.internet().uuid();
        return new Question(questionText, null);
    }
}

