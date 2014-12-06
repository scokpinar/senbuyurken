package senbuyurken.restclient;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import org.springframework.stereotype.Component;
import senbuyurken.entities.User;

import javax.ws.rs.core.MediaType;

/**
 * User: SametCokpinar
 * Date: 06/12/14
 * Time: 00:27
 */
@Component("restClient")
public class RestClient {

    private WebResource webResource;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/senbuyurken/rest";

    /**
     * Default constructor - initializes client to reference appropriate web service.
     */
    public RestClient() {
        com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
        client = Client.create(config);
        webResource = client.resource(BASE_URI).path("userRestWS");
    }

    /**
     * CRUD operation to delete a record from the database
     *
     * @param id of part to be removed
     * @throws UniformInterfaceException if web service throws an exception. i.e. remove failed.
     */
    public void remove(String id) throws UniformInterfaceException {
        webResource.path(java.text.MessageFormat.format("{0}", new Object[]{id})).delete();
    }

    /**
     * Operation to retrieve a record by supplying its name.
     *
     * @param name of part to find.
     * @return the part found.  Part will have an id of 0l if no part was found.
     * @throws UniformInterfaceException if web service throws an exception
     */
    public User findByName(String name) throws UniformInterfaceException {
        WebResource resource = webResource;
        resource = resource.path(java.text.MessageFormat.format("findByName/{0}", new Object[]{name}));
        return resource.accept(javax.ws.rs.core.MediaType.APPLICATION_XML).get(User.class);
    }

    /**
     * creates a part and persists it in the customers database via an http POST method.
     * creates an xml file from the part to be supplied with the POST method
     *
     * @param user to be created
     * @throws UniformInterfaceException if web service throws an exception. i.e. part is not unique.
     */
    public void create_XML(User user) throws UniformInterfaceException {
        webResource.type(MediaType.APPLICATION_JSON).post(user);
    }
    /**
     * creates a part and persists it in the customers database via an http GET method.
     * supplies the pertinent parts of the part to be created in the URL
     * @param parent name of the part that is the parent of this part.  Supply an empty string if their is no parent.
     * @param name of the part to be created.
     * @return the part created by the web service.
     * @throws UniformInterfaceException if web service throws and exception.
     */

    /**
     * release resources associated with this client.
     */
    public void close() {
        client.destroy();
    }
}
