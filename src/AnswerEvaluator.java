import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AnswerEvaluator {

    public void evaluateAnswers(String exerciseFile, String answerFile) {
        List<String> exercises = readFile(exerciseFile);
        List<String> answers = readFile(answerFile);

        List<Integer> correctIndices = new ArrayList<>();
        List<Integer> wrongIndices = new ArrayList<>();

        for (int i = 0; i < exercises.size(); i++) {
            String exercise = exercises.get(i).trim();
            double expectedAnswer = evaluateExpression(exercise);
            String expectedAnswerStr = String.valueOf(expectedAnswer);

            if (expectedAnswerStr.equals(answers.get(i).trim())) {
                correctIndices.add(i + 1);
            } else {
                wrongIndices.add(i + 1);
            }
        }

        writeGradeToFile(correctIndices, wrongIndices);
    }

    private List<String> readFile(String filename) {
        List<String> contents = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contents.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contents;
    }

    private double evaluateExpression(String expression) {
        // 这里可以使用Java内置的脚本引擎来计算表达式
        // 你也可以选择实现自己的解析器
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

    private void writeGradeToFile(List<Integer> correctIndices, List<Integer> wrongIndices) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Grade.txt"))) {
            writer.write("Correct: " + correctIndices.size() + " (" + correctIndices.toString() + ")\n");
            writer.write("Wrong: " + wrongIndices.size() + " (" + wrongIndices.toString() + ")\n");
            System.out.println("Grading completed. Results saved to Grade.txt.");
        } catch (IOException e) {



            e.printStackTrace();
        }
    }
}
