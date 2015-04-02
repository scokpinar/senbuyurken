package senbuyurken.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import senbuyurken.entities.BabyInfo;
import senbuyurken.entities.JSONResult;
import senbuyurken.entities.User;
import senbuyurken.services.BabyInfoService;
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
 * Created by SametCokpinar on 18/02/15.
 */
@Component
@Path("/babyRegistrationRest")
public class BabyInfoRest {

    @Autowired
    private UserService userService;

    @Autowired
    private BabyInfoService babyInfoService;

    @POST
    @Path("/createBabyInfo")
    @Produces({MediaType.APPLICATION_JSON})
    @Transactional
    public Response createBabyInfoFromForm(
            @FormParam("email") String email,
            @FormParam("name") String name,
            @FormParam("surname") String surname,
            @FormParam("gender") String gender,
            @FormParam("birth_date") String birth_date,
            @FormParam("birth_hour") String birth_hour,
            @FormParam("birth_weight") int birth_weight,
            @FormParam("birth_length") int birth_length,
            @FormParam("birth_place") String birth_place,
            @FormParam("hospital") String hospital,
            @FormParam("gynecology_doctor") String gynecology_doctor,
            @FormParam("pediatrician_doctor") String pediatrician_doctor

    ) {

        JSONResult result = new JSONResult(false);

        User user = userService.findByEmailAddress(email);

        if (user != null) {
            BabyInfo babyInfo = babyInfoService.findByUser(user.getUserId());
            if (babyInfo == null)
                babyInfo = new BabyInfo();
            babyInfo.setUser(user);
            babyInfo.setName(name);
            babyInfo.setSurname(surname);
            babyInfo.setGender(gender);
            try {
                DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                Date date = format.parse(birth_date);
                babyInfo.setBirthDate(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            babyInfo.setBirthHour(birth_hour);
            babyInfo.setBirthWeight(birth_weight);
            babyInfo.setBirthLength(birth_length);
            babyInfo.setBirthPlace(birth_place);
            babyInfo.setHospital(hospital);
            babyInfo.setGynecologyDoctor(gynecology_doctor);
            babyInfo.setPediatricianDoctor(pediatrician_doctor);
            babyInfoService.save(babyInfo);

            result.setResult(true);
        }

        return Response.status(200).entity(result).build();
    }

    @POST
    @Path("/getBabyInfo")
    @Produces({MediaType.APPLICATION_JSON})
    @Transactional
    public Response getBabyInfo(
            @FormParam("email") String email
    ) {

        JSONResult result = new JSONResult(false);

        User user = userService.findByEmailAddress(email);
        BabyInfo babyInfo = babyInfoService.findByUser(user.getUserId());
        result.setResult(true);
        return Response.status(200).entity(babyInfo).build();
    }


    public BabyInfoService getBabyInfoService() {
        return babyInfoService;
    }

    public void setBabyInfoService(BabyInfoService babyInfoService) {
        this.babyInfoService = babyInfoService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
