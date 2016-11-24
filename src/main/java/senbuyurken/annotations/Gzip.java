package senbuyurken.annotations;

import javax.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by SametCokpinar on 07/12/15.
 */

@NameBinding
@Retention(RetentionPolicy.RUNTIME)
public @interface Gzip {
}

