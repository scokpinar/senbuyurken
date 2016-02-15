package senbuyurken.utils;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.model.Tokeninfo;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

/**
 * Created by SametCokpinar on 27/01/15.
 */

public class TokenValidator {

    private String mProblem = "Verification failed. (Time-out?)";

    public TokenValidator() {

    }

    public Boolean validate(String tokenString) {
        JsonFactory jsonFactory = new JacksonFactory();
        HttpTransport httpTransport = new NetHttpTransport();

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(httpTransport, jsonFactory)
                .setAudience(Arrays.asList(AppUtils.GOOGLE_CLIENT_ID))
                .setIssuers(Arrays.asList("https://accounts.google.com", "accounts.google.com"))
                .build();

        try {
            GoogleIdToken token = GoogleIdToken.parse(jsonFactory, tokenString);

            if (verifier.verify(token)) {
                GoogleIdToken.Payload payload = token.getPayload();
                boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
                String authorizedParty = payload.getAuthorizedParty();
                if (emailVerified && authorizedParty.equals(AppUtils.GOOGLE_APP_ID))
                    return true;
            } else {
                System.out.println("Invalid ID token.");
            }
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Tokeninfo check(String tokenString) {

        JsonFactory jsonFactory = new JacksonFactory();
        HttpTransport httpTransport = new NetHttpTransport();


        GoogleIdToken idToken = null;

        //idToken = verifier.verify(tokenString);

        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            // Print user identifier
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId);

            // Get profile information from payload
            String email = payload.getEmail();
            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");


        } else {
            System.out.println("Invalid ID token.");
        }

        return null;
    }

    public String problem() {
        return mProblem;
    }
}
