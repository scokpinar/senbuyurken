package senbuyurken.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import senbuyurken.entities.JSONResult;
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
@Path("/userRegistrationRest")
public class UserRegistrationRest {

    @Autowired
    private UserService userService;

    @GET
    @Path("/checkURRService")
    @Produces({MediaType.APPLICATION_JSON})
    public Response checkRestService() {
        JSONResult result = new JSONResult(true);
        System.out.println("Rest URR working");
        return Response.status(200).entity(result).build();
    }

    @POST
    @Path("/createUser")
    @Produces({MediaType.APPLICATION_JSON})
    @Transactional
    public Response createUserFromForm(
            @FormParam("email") String email,
            @FormParam("active") String active,
            @FormParam("user_type") String user_type
    ) {
        JSONResult result = new JSONResult(false);
        if (userService.checkUser(email)) {
            User user = new User(email, active, user_type);
            User u = userService.save(user);
            result.setResult(true);
            return Response.status(200).entity(result).build();
        }
        return Response.status(200).entity(result).build();
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}