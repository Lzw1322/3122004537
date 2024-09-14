package Utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class IOUtil {


    public static String readTxt(String txtPath) {
        StringBuilder str = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(Files.newInputStream(new File(txtPath).toPath()), StandardCharsets.UTF_8))) {
            String strLine;
            while ((strLine = bufferedReader.readLine()) != null) {
                str.append(strLine).append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }


    public static void writeTxt(String str, String txtPath) {
        try (FileWriter fileWriter = new FileWriter(txtPath, true)) {
            fileWriter.write(str);
            fileWriter.write(System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

