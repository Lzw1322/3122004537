package Utils;

public class CheckUtil {
    public static double getSimilarity(String str1,String str2){
        String simHash = SimHashUtil.getSimHash(str1);
        String simHash1 = SimHashUtil.getSimHash(str2);
        int hammingDistance = HammingUtil.getHammingDistance(simHash, simHash1);
        double similarity = HammingUtil.getSimilarity(hammingDistance);
        return similarity;
    }
}
