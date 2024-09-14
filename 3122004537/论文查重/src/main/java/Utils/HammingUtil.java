package Utils;

public class HammingUtil {

    public static int getHammingDistance(String simHash1, String simHash2) {
        if (simHash1.length() != simHash2.length()) {
            return -1; // 长度不一致，返回 -1 表示无法计算
        }
        int distance = 0;
        for (int i = 0; i < simHash1.length(); i++) {
            if (simHash1.charAt(i) != simHash2.charAt(i)) {
                distance++;
            }
        }
        return distance;
    }


    public static double getSimilarity(int distance) {
        // 128 是一个示例值，根据具体情况调整
        return 1 - distance / 128.0;
    }
}