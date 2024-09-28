import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MathQuestionGenerator {
    private final Random random = new Random();

    public Set<String> generateQuestions(int num, int rangeLimit) {
        Set<String> questions = new HashSet<>();

        while (questions.size() < num) {
            String question = generateRandomQuestion(rangeLimit);
            if (question != null) {
                questions.add(question);
            }
        }

        return questions;
    }

    private String generateRandomQuestion(int rangeLimit) {
        int operatorCount = random.nextInt(3) + 1; // 运算符个数为1到3
        StringBuilder question = new StringBuilder();

        for (int i = 0; i <=operatorCount; i++) {
            int e1 = random.nextInt(rangeLimit) ;
            int e2 = random.nextInt(rangeLimit) ;
            String operator = getRandomOperator();

            // 确保没有负数产生和处理真分数
            if (operator.equals("-") && e1 < e2) {
                return null; // 跳过，继续生成
            }

            // 构建题目
            if (i == 0) {
                question.append(e1);
                continue;
            } else {
                question.append(" ").append(operator).append(" ");
            }

            if (operator.equals("/")) {
                // 为了生成真分数，确保 e1 < e2
                if(e2==0) return null;
                if (e1 >= e2 ) {
                    return null;
                }
                question.append(e1).append(" / ").append(e2); // 直接使用格式
            } else {
                question.append(e2);
            }

        }

        return question.toString();
    }

    private String getRandomOperator() {
        String[] operators = {"+", "-", "*", "/"};
        return operators[random.nextInt(operators.length)];
    }
}