package senbuyurken.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import senbuyurken.entities.JSONResult;
import senbuyurken.entities.ParentInfo;
import senbuyurken.entities.User;
import senbuyurken.services.ParentInfoService;
import senbuyurken.services.UserService;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by SametCokpinar on 01/03/15.
 */
@Component
@Path("/parentRegistrationRest")
public class ParentInfoRest {

    @Autowired
    private UserService userService;

    @Autowired
    private ParentInfoService parentInfoService;

    @POST
    @Path("/createParentInfo")
    @Produces({MediaType.APPLICATION_JSON})
    @Transactional
    public Response createBabyInfoFromForm(
            @FormParam("email") String email,
            @FormParam("mother_name") String mother_name,
            @FormParam("mother_surname") String mother_surname,
            @FormParam("father_name") String father_name,
            @FormParam("father_surname") String father_surname,
            @FormParam("wedding_anniversary") String wedding_anniversary
    ) {

        JSONResult result = new JSONResult(false);

        User user = userService.findByEmailAddress(email);

        if (user != null) {
            ParentInfo parentInfo = parentInfoService.findByUser(user.getUserId());
            if (parentInfo == null)
                parentInfo = new ParentInfo();
            parentInfo.setUser(user);
            parentInfo.setMotherName(mother_name);
            parentInfo.setMotherSurname(mother_surname);
            parentInfo.setFatherName(father_name);
            parentInfo.setFatherSurname(father_surname);
            try {
                DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                Date date = format.parse(wedding_anniversary);
                parentInfo.setWeddingAnniversary(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //parentInfo.setPhotoURLMother(url_1);
            //parentInfo.setPhotoURLFather(url_1);
            parentInfoService.save(parentInfo);

            result.setResult(true);
        }

        return Response.status(200).entity(result).build();
    }

    @POST
    @Path("/getParentInfo")
    @Produces({MediaType.APPLICATION_JSON})
    @Transactional
    public Response getParentInfo(
            @FormParam("email") String email
    ) {

        JSONResult result = new JSONResult(false);

        User user = userService.findByEmailAddress(email);
        ParentInfo parentInfo = parentInfoService.findByUser(user.getUserId());
        result.setResult(true);
        return Response.status(200).entity(parentInfo).build();
    }


    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public ParentInfoService getParentInfoService() {
        return parentInfoService;
    }

    public void setParentInfoService(ParentInfoService parentInfoService) {
        this.parentInfoService = parentInfoService;
    }
}
