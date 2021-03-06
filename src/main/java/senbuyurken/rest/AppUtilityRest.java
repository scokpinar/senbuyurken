package senbuyurken.rest;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient;
import com.amazonaws.services.securitytoken.model.GetSessionTokenRequest;
import com.amazonaws.services.securitytoken.model.GetSessionTokenResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import senbuyurken.annotations.Gzip;
import senbuyurken.entities.DiaryEntry;
import senbuyurken.entities.JSONResult;
import senbuyurken.services.DiaryEntryService;
import senbuyurken.services.UserService;
import senbuyurken.utils.AppUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by SametCokpinar on 10/12/15.
 */
@Component
@Path("/appUtilityRest")
public class AppUtilityRest {

    @Autowired
    private DiaryEntryService diaryEntryService;
    @Autowired
    private UserService userService;

    @GET
    @Path("/wake")
    @Produces({"application/json"})
    public Response wake() {
        JSONResult result = new JSONResult(true);
        System.out.println("Wake Called");
        userService.findUserCount4HerokuWake();
        return Response.status(200).entity(result).build();
    }

    /**
     * GZIP Testing purposes
     *
     * @return response
     */
    @GET
    @Path("/listDiaryEntryGet")
    @Gzip
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Response findByUserId() {
        JSONResult result = new JSONResult(false);
        GenericEntity<List<DiaryEntry>> entries = new GenericEntity<List<DiaryEntry>>(diaryEntryService.findByEmail("sametcokpinar@gmail.com")) {
        };
        result.setResult(true);
        return Response.status(200).entity(entries).build();
    }

    @POST
    @Path("/googleTokenValidation")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getGoogleTokenValidation(
            @FormParam("userName") String userName,
            @FormParam("token") String token) {

        JSONResult result = new JSONResult(false);
        result.setResult(AppUtils.tokenValidator(token));
        return Response.status(200).entity(result).build();
    }

    @POST
    @Path("/getToken")
    @Produces({"application/json"})
    public Response getAWSToken(
            @FormParam("userName") String userName,
            @FormParam("token") String token,
            @FormParam("validUser") String validUser) {

        if (AppUtils.tokenValidator(token) && validUser.equals("true")) {
            AWSSecurityTokenServiceClient stsClient = new AWSSecurityTokenServiceClient(new BasicAWSCredentials(AppUtils.accessKey, AppUtils.secretKey));
            stsClient.setEndpoint("sts.eu-west-1.amazonaws.com");

            GetSessionTokenRequest getSessionTokenRequest = new GetSessionTokenRequest();
            getSessionTokenRequest.setDurationSeconds(600);

            GetSessionTokenResult awsToken = stsClient.getSessionToken();
            return Response.status(200).entity(awsToken).build();
        }
        return Response.status(200).entity(null).build();
    }

    public DiaryEntryService getDiaryEntryService() {
        return diaryEntryService;
    }

    public void setDiaryEntryService(DiaryEntryService diaryEntryService) {
        this.diaryEntryService = diaryEntryService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
