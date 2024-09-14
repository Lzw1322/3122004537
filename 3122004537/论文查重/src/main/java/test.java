import Utils.*;

import java.text.NumberFormat;

public class test {
    private static final String ORIGINAL_TEXT_PATH = "src/main/resources/OriginalTxt";
    private static final String COPY_TEXT_PATH_PREFIX = "src/main/resources/CopyTxt";
    private static final String RESULT_PATH = "src/main/resources/Result";

    public static void main(String[] args) {
        String originalText = IOUtil.readTxt(ORIGINAL_TEXT_PATH);
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMaximumFractionDigits(2);

        for (int i = 1; i <= 5; i++) {
            String copyTextPath = COPY_TEXT_PATH_PREFIX + i;
            String copyText = IOUtil.readTxt(copyTextPath);
            double similarity = CheckUtil.getSimilarity(originalText, copyText);
            String formattedSimilarity = nf.format(similarity);

            String result = String.format("第%d篇文章与原文的相似程度为：%s", i, formattedSimilarity);
            System.out.println(result);
            IOUtil.writeTxt(result, RESULT_PATH); // 确保此方法按需写入或追加
        }
    }
}
