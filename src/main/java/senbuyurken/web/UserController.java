/**
 *
 */
package senbuyurken.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import senbuyurken.entities.User;
import senbuyurken.restclient.RestClient;
import senbuyurken.services.UserService;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.List;

/**
 * @author Siva
 */
@ManagedBean
@ViewScoped
@Component
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RestClient rc;


    private User user;
    private List<User> users = null;


    public UserController() {

    }

    public List<User> getUsers() {
        users = userService.findAllUsers();
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User getUser() {
        if (user == null)
            user = new User();
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String addUser() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String status = "success";
        try {

            rc.create_XML(user);
            facesContext
                    .addMessage(status, new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            "New User added successfully",
                            user.getEmail()));


        } catch (Exception ex) {
            status = "fail";
            facesContext
                    .addMessage(status, new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "New User cannot be added", ex.getMessage()));
        }
        return status;
    }
}
