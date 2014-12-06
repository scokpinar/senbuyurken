package senbuyurken.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import senbuyurken.entities.User;
import senbuyurken.services.UserService;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

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


    @GET
    @Produces({MediaType.APPLICATION_JSON}
    )
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    /*@POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.TEXT_HTML})
    @Transactional
    public Response createUserFromForm(
            @FormParam("email") String email,
            @FormParam("password") String password,
            @FormParam("active") String active,
            @FormParam("userType") String userType
            ) {
        User user = new User(email,password);


        User u = userService. create(user);

        return Response.status(201).entity("A new user has been created").build();
    }*/

    @POST
    public Response createUserFromForm(User user) {


        User u = userService.create(user);

        return Response.status(201).entity("A new user has been created").build();

    }


    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}