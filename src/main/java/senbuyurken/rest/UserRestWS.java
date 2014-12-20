package senbuyurken.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import senbuyurken.entities.BabyInfo;
import senbuyurken.entities.User;
import senbuyurken.services.BabyInfoService;
import senbuyurken.services.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: SametCokpinar
 * Date: 01/12/14
 * Time: 23:03
 */
@Component
@Path("/userRestWS")
public class UserRestWS {

    @Autowired
    private UserService userService;

    @Autowired
    private BabyInfoService babyInfoService;


//    @GET
//    @Produces({MediaType.APPLICATION_JSON}
//    )
//    public List<User> getAllUsers() {
//        return userService.findAllUsers();
//    }

    @GET
    @Produces({MediaType.TEXT_HTML})
    public String getAllUsers() {
        return "Rest working";
    }

    @POST
    @Path("/createUser")
    @Produces({MediaType.APPLICATION_JSON})
    @Transactional
    public Response createUserFromForm(
            @FormParam("name") String name,
            @FormParam("surname") String surname,
            @FormParam("gender") String gender,
            @FormParam("birthDate") String birthDate,
            @FormParam("email") String email,
            @FormParam("password") String password,
            @FormParam("active") String active,
            @FormParam("userType") String userType
    ) {


        User user = new User(email, password, active, userType);
        User u = userService.create(user);

        BabyInfo babyInfo = new BabyInfo();
        babyInfo.setUser(u);
        babyInfo.setName(name);
        babyInfo.setSurname(surname);
        babyInfo.setGender(gender);
        try {
            DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Date date = format.parse(birthDate);
            babyInfo.setBirthDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        babyInfoService.create(babyInfo);

        return Response.status(200).entity(u).build();

    }


    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public BabyInfoService getBabyInfoService() {
        return babyInfoService;
    }

    public void setBabyInfoService(BabyInfoService babyInfoService) {
        this.babyInfoService = babyInfoService;
    }
}