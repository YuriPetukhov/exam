package ru.skypro.exam.constant;

import ru.skypro.exam.model.Question;

import java.util.List;

public class QuestionsConstants {
    public static final String Q_J_1 = "Java Q 1";
    public static final String Q_J_2 = "Java Q 2";
    public static final String Q_J_3 = "Java Q 3";
    public static final String Q_J_4 = "Java Q 4";
    public static final String Q_J_5 = "Java Q 5";
    public static final String Q_J_6 = "Java Q 6";

    public static final String A_J_1 = "Java A 1";
    public static final String A_J_2 = "Java A 2";
    public static final String A_J_3 = "Java A 3";
    public static final String A_J_4 = "Java A 4";
    public static final String A_J_5 = "Java A 5";
    public static final String A_J_6 = "Java A 6";
    public static final Question JAVA1 = new Question(Q_J_1, A_J_1);
    public static final Question JAVA2 = new Question(Q_J_2, A_J_2);
    public static final Question JAVA3 = new Question(Q_J_3, A_J_3);
    public static final Question JAVA4 = new Question(Q_J_4, A_J_4);
    public static final Question JAVA5 = new Question(Q_J_5, A_J_5);
    public static final Question JAVA6 = new Question(Q_J_6, A_J_6);

    public static final List<Question> JAVA_LIST = List.of(
            JAVA1,
            JAVA2,
            JAVA3,
            JAVA4,
            JAVA5
    );
    public static final String Q_M_1 = "Math Q 1";
    public static final String Q_M_2 = "Math Q 2";
    public static final String Q_M_3 = "Math Q 3";
    public static final String Q_M_4 = "Math Q 4";
    public static final String Q_M_5 = "Math Q 5";
    public static final String Q_M_6 = "Math Q 6";

    public static final String A_M_1 = "Math A 1";
    public static final String A_M_2 = "Math A 2";
    public static final String A_M_3 = "Math A 3";
    public static final String A_M_4 = "Math A 4";
    public static final String A_M_5 = "Math A 5";
    public static final String A_M_6 = "Math A 6";
    public static final Question MATH1 = new Question(Q_M_1, A_M_1);
    public static final Question MATH2 = new Question(Q_M_2, A_M_2);
    public static final Question MATH3 = new Question(Q_M_3, A_M_3);
    public static final Question MATH4 = new Question(Q_M_4, A_M_4);
    public static final Question MATH5 = new Question(Q_M_5, A_M_5);
    public static final Question MATH6 = new Question(Q_M_6, A_M_6);

    public static final List<Question> MATH_LIST = List.of(
            MATH1,
            MATH2,
            MATH3,
            MATH4,
            MATH5
    );
    public static final List<Question> JAVA_MATH_LIST = List.of(
            JAVA1,
            JAVA2,
            JAVA3,
            JAVA4,
            JAVA5,
            MATH1,
            MATH2,
            MATH3,
            MATH4,
            MATH5
    );
}
