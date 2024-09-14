package Utils;

import com.hankcs.hanlp.HanLP;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;

public class SimHashUtil {

    public static String getHash(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] digest = messageDigest.digest(str.getBytes(StandardCharsets.UTF_8));
            return new BigInteger(1, digest).toString(2);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    public static String getSimHash(String str) {
        if (str.length() < 66) {
            System.out.println("输入文本过短");
            return "";
        }

        int[] weight = new int[128];
        List<String> keywordList = HanLP.extractKeyword(str, str.length());
        int size = keywordList.size();

        for (int i = 0; i < size; i++) {
            String keyword = keywordList.get(i);
            String hash = getHash(keyword);
            hash = String.format("%128s", hash).replace(' ', '0');

            int weightAdjustment = 10 - (i / (size / 10));
            for (int j = 0; j < weight.length; j++) {
                weight[j] += (hash.charAt(j) == '1') ? weightAdjustment : -weightAdjustment;
            }
        }

        StringBuilder simHash = new StringBuilder();
        for (int w : weight) {
            simHash.append(w > 0 ? '1' : '0');
        }

        return simHash.toString();
    }
}
