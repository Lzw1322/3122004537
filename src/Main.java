import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of questions: ");
        int numQuestions = scanner.nextInt();

        System.out.print("Enter range limit: ");
        int rangeLimit = scanner.nextInt();

        MathQuestionGenerator generator = new MathQuestionGenerator();
        Set<String> questions = generator.generateQuestions(numQuestions, rangeLimit);

        FileHandler fileHandler = new FileHandler();
        fileHandler.saveQuestionsToFile("Exercises.txt", questions);

        Set<String> answers = new HashSet<>();
        for (String question : questions) {
            answers.add(String.valueOf(evaluateExpression(question)));
        }
        fileHandler.saveAnswersToFile("Answers.txt", answers);

//        System.out.println("Enter exercise file name for grading:");
//        String exerciseFile = scanner.next();
//
//        System.out.println("Enter answers file name for grading:");
//        String answerFile = scanner.next();
//
//        AnswerEvaluator evaluator = new AnswerEvaluator();
//        evaluator.evaluateAnswers(exerciseFile, answerFile);

        scanner.close();
    }

    private static double evaluateExpression(String expression) {
        // 简单的表达式计算
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < expression.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if (eat('+')) x += parseTerm();
                    else if (eat('-')) x -= parseTerm();
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if (eat('*')) x *= parseFactor();
                    else if (eat('/')) x /= parseFactor();
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor();
                if (eat('-')) return -parseFactor();

                double x;
                int startPos = this.pos;
                if (eat('(')) {
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') {
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(expression.substring(startPos, this.pos));
                } else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }
                return x;
            }
        }.parse();
    }
}

