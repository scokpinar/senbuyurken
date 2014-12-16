package senbuyurken.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import senbuyurken.entities.User;
import senbuyurken.services.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    @Transactional
    public Response createUserFromForm(
            @FormParam("email") String email,
            @FormParam("password") String password,
            @FormParam("active") String active,
            @FormParam("userType") String userType
    ) {
        User user = new User(email, password, active, userType);
        User u = userService.create(user);
        String output = u.toString();
        return Response.status(200).entity(output).build();

    }


    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}