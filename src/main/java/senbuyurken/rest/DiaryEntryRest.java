package senbuyurken.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import senbuyurken.entities.DiaryEntry;
import senbuyurken.entities.JSONResult;
import senbuyurken.services.DiaryEntryService;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.GregorianCalendar;

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


    @POST
    @Path("/createDiaryEntry")
    @Produces({MediaType.APPLICATION_JSON})
    @Transactional
    public Response createDiaryEntryFromForm(
            @FormParam("entry_content") String entry_content
    ) {

        JSONResult result = new JSONResult(false);

        try {
            DiaryEntry diaryEntry = new DiaryEntry();
            diaryEntry.setEntry_content(entry_content);
            diaryEntry.setEntry_date(new GregorianCalendar().getTime());
            DiaryEntry de = diaryEntryService.save(diaryEntry);

            result.setResult(true);
            return Response.status(200).entity(result).build();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Response.status(200).entity(result).build();
    }


    public DiaryEntryService getDiaryEntryService() {
        return diaryEntryService;
    }

    public void setDiaryEntryService(DiaryEntryService diaryEntryService) {
        this.diaryEntryService = diaryEntryService;
    }
}
