package senbuyurken.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import senbuyurken.entities.DiaryEntry;
import senbuyurken.entities.JSONResult;
import senbuyurken.services.DiaryEntryService;
import senbuyurken.services.UserService;
import senbuyurken.utils.AppUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * User: SametCokpinar
 * Date: 25/12/14
 * Time: 22:03
 */
@Component
@Path("/diaryEntryRest")
public class DiaryEntryRest {


    @Autowired
    private DiaryEntryService diaryEntryService;
    @Autowired
    private UserService userService;


    @GET
    @Path("/checkDERService")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Response checkRestService() {
        System.out.println("Rest DER working");
        JSONResult result = new JSONResult(true);
        return Response.status(200).entity(result).build();
    }

    @POST
    @Path("/createDiaryEntry")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @Transactional
    public Response createDiaryEntryFromForm(
            @FormParam("un") String email,
            @FormParam("t") String token,
            @FormParam("entry_title") String entry_title,
            @FormParam("entry_text") String entry_text,
            @FormParam("entry_date") String entry_date,
            @FormParam("time_zone") String time_zone,
            @FormParam("image") String image) {

        JSONResult result = new JSONResult(false);

        DiaryEntry diaryEntry = new DiaryEntry();

        diaryEntry.setEntry_title(entry_title);
        diaryEntry.setEntry_content(entry_text);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone(time_zone));
        calendar.setTimeInMillis(Long.parseLong(entry_date));
        diaryEntry.setEntry_date(calendar.getTime());
        diaryEntry.setPhoto_url(image);
        diaryEntry.setUser(userService.findByEmailAddress(email));

        String userId = AppUtils.tokenChecker(email, token);
        if (userId != null) {
            diaryEntryService.save(diaryEntry);
            result.setResult(true);
        }
        return Response.status(200).entity(result).build();
    }

    @POST
    @Path("/listDiaryEntry")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Response findByUserId(
            @FormParam("un") String email,
            @FormParam("t") String token) {

        JSONResult result = new JSONResult(false);

        if (AppUtils.tokenChecker(email, token) != null) {
            GenericEntity<List<DiaryEntry>> entries = new GenericEntity<List<DiaryEntry>>(diaryEntryService.findByEmail(email)) {
            };
            result.setResult(true);
            return Response.status(200).entity(entries).build();
        }
        return Response.status(200).entity(result).build();
    }

    @POST
    @Path("/getDiaryEntryImage")
    @Produces({MediaType.APPLICATION_OCTET_STREAM})
    public Response getImagesByUserId(
            @FormParam("un") String email,
            @FormParam("t") String token,
            @FormParam("photo_url") String photoURL) {

        JSONResult result = new JSONResult(false);
        String userId = AppUtils.tokenChecker(email, token);
        if (userId != null) {
            result.setResult(true);
        }
        return Response.status(200).entity(result).build();
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
