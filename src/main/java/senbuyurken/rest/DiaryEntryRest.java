package senbuyurken.rest;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
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
import senbuyurken.services.UserService;
import senbuyurken.utils.AppUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;
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
    @Autowired
    private UserService userService;

    //private String userId;


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
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces({MediaType.APPLICATION_JSON})
    @Transactional
    public Response createDiaryEntryFromForm(@Context HttpServletRequest request) {

        JSONResult result = new JSONResult(false);
        InputStream orginalIS = null;
        InputStream resizedIS = null;
        String fileName = null;
        String user = null;
        String token = null;


        DiaryEntry diaryEntry = new DiaryEntry();

        if (ServletFileUpload.isMultipartContent(request)) {
            final FileItemFactory factory = new DiskFileItemFactory();
            final ServletFileUpload fileUpload = new ServletFileUpload(factory);
            try {

                final List items = fileUpload.parseRequest(request);

                if (items != null) {
                    for (Object item1 : items) {
                        final FileItem item = (FileItem) item1;
                        if (item.isFormField() && "entry_text".equals(item.getFieldName())) {
                            diaryEntry.setEntry_content(item.getString());
                            diaryEntry.setEntry_date(new Timestamp(new Date().getTime()));
                        } else if ("image".equals(item.getFieldName())) {
                            orginalIS = item.getInputStream();
                            resizedIS = item.getInputStream();
                            fileName = item.getName();
                            diaryEntry.setPhoto_url(fileName);
                        } else if ("un".equals(item.getFieldName())) {
                            user = item.getString();
                            diaryEntry.setUser(userService.findByEmailAddress(user));
                        } else if ("t".equals(item.getFieldName())) {
                            token = item.getString();
                        }
                    }

                    String userId = AppUtils.tokenChecker(user, token);
                    if (userId != null) {
                        saveToAWSS3(orginalIS, resizedIS, fileName, userId);
                        diaryEntryService.save(diaryEntry);
                        result.setResult(true);
                    }
                }
            } catch (FileUploadException fue) {
                fue.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Response.status(200).entity(result).build();
    }

    @POST
    @Path("/listDiaryEntry")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Response findByUserId(
            @FormParam("un") String email,
            @FormParam("t") String token) {

        JSONResult result = new JSONResult(false);

        if (AppUtils.tokenChecker(email, token) != null) {
            GenericEntity<List<DiaryEntry>> entries = new GenericEntity<List<DiaryEntry>>(diaryEntryService.findByEmail(email)) {
            };
            result.setResult(true);
            return Response.status(200).entity(entries).build();
        }
        return Response.status(200).entity(result).build();
    }

    @POST
    @Path("/getDiaryEntryImage")
    @Produces({MediaType.APPLICATION_OCTET_STREAM})
    public Response getImagesByUserId(
            @FormParam("un") String email,
            @FormParam("t") String token,
            @FormParam("photo_url") String photoURL) {

        JSONResult result = new JSONResult(false);
        String userId = AppUtils.tokenChecker(email, token);
        if (userId != null) {
            result.setResult(true);
            //return Response.status(200).entity(loadFromAWSS3(photoURL, userId)).build();
        }
        return Response.status(200).entity(result).build();
    }


    private void saveToAWSS3(InputStream inputStream, InputStream inputStream2, String itemName, String userId) {

        String subFolderOriginal = userId + "/";

        AmazonS3 s3Client = new AmazonS3Client(new BasicAWSCredentials(AppUtils.accessKey, AppUtils.secretKey));

        try {
            System.out.println("Uploading a new object to S3 from a file\n");

            BufferedImage originalImage;
            Image scaledImage;

            PutObjectRequest por4Original = new PutObjectRequest(
                    AppUtils.existingBucketName, subFolderOriginal + itemName, inputStream, new ObjectMetadata());

            por4Original.setCannedAcl(CannedAccessControlList.AuthenticatedRead);
            s3Client.putObject(por4Original);

            try {
                originalImage = ImageIO.read(inputStream2);
                scaledImage = originalImage.getScaledInstance(originalImage.getWidth() / 4, originalImage.getHeight() / 4, Image.SCALE_SMOOTH);
                BufferedImage bImage = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_RGB);
                Graphics2D bGr = bImage.createGraphics();
                bGr.drawImage(scaledImage, 0, 0, null);
                bGr.dispose();
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ImageIO.write(bImage, "jpeg", os);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //PutObjectRequest por4Resized = new PutObjectRequest(existingBucketName, subFolderResized + itemName, fis, new ObjectMetadata());
            //por4Resized.setCannedAcl(CannedAccessControlList.AuthenticatedRead);
            //s3Client.putObject(por4Resized);

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

    private InputStream loadFromAWSS3(String photoURL, String userId) {
        String subFolder = userId + "/";

        AmazonS3 s3Client = new AmazonS3Client(new BasicAWSCredentials(AppUtils.accessKey, AppUtils.secretKey));

        try {
            System.out.println("Getting objects from S3 as stream\n");

            S3Object object = s3Client.getObject(new GetObjectRequest(
                    AppUtils.existingBucketName, subFolder + photoURL));

            return object.getObjectContent();

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
        return null;
    }

    public DiaryEntryService getDiaryEntryService() {
        return diaryEntryService;
    }

    public void setDiaryEntryService(DiaryEntryService diaryEntryService) {
        this.diaryEntryService = diaryEntryService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
