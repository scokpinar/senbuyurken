package senbuyurken.utils;

/**
 * Created by SametCokpinar on 10/12/15.
 */
public class AppUtils {

    public static final String existingBucketName = System.getenv("BUCKET");
    public static final String accessKey = System.getenv("ACCESS_KEY_ID");
    public static final String secretKey = System.getenv("SECRET_ACCESS_KEY");

    public static final String GOOGLE_CLIENT_ID = System.getenv("GOOGLE_CLIENT_ID");
    public static final String GOOGLE_APP_ID = System.getenv("GOOGLE_APP_ID");

    public static Boolean tokenValidator(String token) {
        TokenValidator ch = new TokenValidator();
        return ch.validate(token);
    }
}
