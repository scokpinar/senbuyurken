package senbuyurken.annotations;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by SametCokpinar on 07/12/15.
 */
@Provider
@Gzip
public class GZIPWriterInterceptor implements WriterInterceptor {

    public GZIPWriterInterceptor() {
        super();
    }

    @Override
    public void aroundWriteTo(WriterInterceptorContext context)
            throws IOException, WebApplicationException {

        MultivaluedMap<String, Object> headers = context.getHeaders();
        headers.add("Content-Encoding", "gzip");

        final OutputStream outputStream = context.getOutputStream();
        context.setOutputStream(new GZIPOutputStream(outputStream));
        context.proceed();
    }

}