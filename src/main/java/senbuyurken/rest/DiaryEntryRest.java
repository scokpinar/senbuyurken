package senbuyurken.rest;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import senbuyurken.entities.DiaryEntry;
import senbuyurken.entities.JSONResult;
import senbuyurken.services.DiaryEntryService;
import senbuyurken.services.TokenChecker;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
    @Path("/checkDERService")
    @Produces({MediaType.APPLICATION_JSON})
    public Response checkRestService() {
        System.out.println("checkDERService called");
        JSONResult result = new JSONResult(true);
        return Response.status(200).entity(result).build();
    }

    @POST
    @Path("/createDiaryEntry")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces({MediaType.APPLICATION_JSON})
    @Transactional
    public Response createDiaryEntryFromForm(@Context HttpServletRequest request) {

        JSONResult result = new JSONResult(false);
        DiaryEntry diaryEntry = new DiaryEntry();

        if (ServletFileUpload.isMultipartContent(request)) {
            final FileItemFactory factory = new DiskFileItemFactory();
            final ServletFileUpload fileUpload = new ServletFileUpload(factory);
            try {

                final List items = fileUpload.parseRequest(request);

                if (items != null) {
                    final Iterator iter = items.iterator();
                    while (iter.hasNext()) {
                        final FileItem item = (FileItem) iter.next();
                        final String itemName = item.getName();
                        final String fieldValue = item.getString();

                        if (item.isFormField()) {
                            diaryEntry.setEntry_content(fieldValue);
                            diaryEntry.setEntry_date(new Timestamp(new Date().getTime()));
                        } else {
                            saveToAWSS3(item.getInputStream(), itemName);
                        }

                    }
                }
            } catch (FileUploadException fue) {
                fue.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        diaryEntryService.save(diaryEntry);
        return Response.status(200).entity(result).build();
    }


    @POST
    @Path("/listDiaryEntry")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Response findByUserId(
            @FormParam("un") String email,
            @FormParam("t") String token) {

        JSONResult result = new JSONResult(false);

        TokenChecker ch = new TokenChecker(new String[]{email}, "345121036471-p2rragjceuga9g0vrf04e8ml7komc07m.apps.googleusercontent.com");
        GoogleIdToken.Payload pl = ch.check(token);

        if (pl != null && pl.getEmailVerified()) {
            GenericEntity<List<DiaryEntry>> entries = new GenericEntity<List<DiaryEntry>>(diaryEntryService.findByEmail(email)) {
            };
            result.setResult(true);
            return Response.status(200).entity(entries).build();
        }
        return Response.status(200).entity(result).build();
    }


    private void saveToAWSS3(InputStream inputStream, String itemName) {
        String existingBucketName = "senbuyurken-photos";
        String accessKey = "AKIAJZPJ7BZBDSLQQOOA";
        String secretKey = "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";

        AmazonS3 s3Client = new AmazonS3Client(new BasicAWSCredentials(accessKey, secretKey));


        try {
            System.out.println("Uploading a new object to S3 from a file\n");
            s3Client.putObject(new PutObjectRequest(
                    existingBucketName, itemName, inputStream, new ObjectMetadata()));

        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which " +
                    "means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which " +
                    "means the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
    }


    public DiaryEntryService getDiaryEntryService() {
        return diaryEntryService;
    }

    public void setDiaryEntryService(DiaryEntryService diaryEntryService) {
        this.diaryEntryService = diaryEntryService;
    }
}
