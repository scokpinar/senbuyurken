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
            @FormParam("userName") String userName,
            @FormParam("token") String token,
            @FormParam("validUser") String validUser,
            @FormParam("entryTitle") String entryTitle,
            @FormParam("entryText") String entryText,
            @FormParam("entryDate") String entryDate,
            @FormParam("timeZone") String timeZone,
            @FormParam("photoURL") String photoURL) {

        JSONResult result = new JSONResult(false);

        if (AppUtils.tokenValidator(token) && validUser.equals("true")) {
            DiaryEntry diaryEntry = new DiaryEntry();
            diaryEntry.setEntry_title(entryTitle);
            diaryEntry.setEntry_content(entryText);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeZone(TimeZone.getTimeZone(timeZone));
            calendar.setTimeInMillis(Long.parseLong(entryDate));
            diaryEntry.setEntry_date(calendar.getTime());
            diaryEntry.setPhoto_url(photoURL);
            diaryEntry.setUser(userService.findByEmailAddress(userName));
            diaryEntryService.save(diaryEntry);
            result.setResult(true);
        }
        return Response.status(200).entity(result).build();
    }

    @POST
    @Path("/listDiaryEntry")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Response findByUserId(
            @FormParam("userName") String userName,
            @FormParam("token") String token,
            @FormParam("validUser") String validUser) {

        JSONResult result = new JSONResult(false);
        if (AppUtils.tokenValidator(token) && validUser.equals("true")) {
            GenericEntity<List<DiaryEntry>> entries = new GenericEntity<List<DiaryEntry>>(diaryEntryService.findByEmail(userName)) {
            };
            result.setResult(true);
            return Response.status(200).entity(entries).build();
        }
        return Response.status(200).entity(result).build();
    }

    @POST
    @Path("/deleteDiaryEntry")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Response deleteById(
            @FormParam("entryId") String entryId,
            @FormParam("token") String token,
            @FormParam("validUser") String validUser) {

        JSONResult result = new JSONResult(false);
        if (AppUtils.tokenValidator(token) && validUser.equals("true")) {
            diaryEntryService.delete(Integer.parseInt(entryId));
            result.setResult(true);
            return Response.status(200).entity(result).build();
        }
        return Response.status(200).entity(result).build();
    }

}
