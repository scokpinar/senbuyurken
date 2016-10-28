package senbuyurken.utils;

/**
 * Created by SametCokpinar on 10/12/15.
 */
public class AppUtils {

    public static final String existingBucketName = System.getenv("BUCKET");
    public static final String accessKey = System.getenv("ACCESS_KEY_ID");
    public static final String secretKey = System.getenv("SECRET_ACCESS_KEY");

    public static final String GOOGLE_CLIENT_ID = "345121036471-p2rragjceuga9g0vrf04e8ml7komc07m.apps.googleusercontent.com";
    //Local
    public static final String GOOGLE_APP_ID = "345121036471-er4m3a2bn1f2itqio1c8ije49oahfpu3.apps.googleusercontent.com";
    //Beta
    //public static final String GOOGLE_APP_ID = "345121036471-vl8dv1c20chsvlh2jkhboqpqlsfc12u5.apps.googleusercontent.com";


    public static Boolean tokenValidator(String token) {
        TokenValidator ch = new TokenValidator();
        return ch.validate(token);
    }
}
