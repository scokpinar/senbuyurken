package senbuyurken.utils;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

/**
 * Created by SametCokpinar on 10/12/15.
 */
public class AppUtils {

    public static String existingBucketName = System.getenv("BUCKET");
    public static String accessKey = System.getenv("ACCESS_KEY_ID");
    public static String secretKey = System.getenv("SECRET_ACCESS_KEY");

    public static String tokenChecker(String email, String token) {
        TokenChecker ch = new TokenChecker(new String[]{email}, "345121036471-p2rragjceuga9g0vrf04e8ml7komc07m.apps.googleusercontent.com");
        GoogleIdToken.Payload pl = ch.check(token);
        String userId = null;

        if (pl != null && pl.getEmailVerified()) {
            userId = (String) pl.get("sub");
        }

        System.out.println("Google UserId = " + userId);

        return userId;
    }

}
