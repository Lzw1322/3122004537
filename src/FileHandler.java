import java.io.*;
import java.util.Set;

public class FileHandler {

    public void saveQuestionsToFile(String filename, Set<String> questions) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String question : questions) {
                writer.write(question);
                writer.newLine();
            }
            System.out.println("Questions saved to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAnswersToFile(String filename, Set<String> answers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String answer : answers) {
                writer.write(answer);
                writer.newLine();
            }
            System.out.println("Answers saved to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

