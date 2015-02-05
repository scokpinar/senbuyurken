package senbuyurken.entities;


import org.apache.commons.lang3.time.FastDateFormat;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Date;

/**
 * Created by SametCokpinar on 14/01/15.
 */
public class MyDateAdapterLong extends XmlAdapter<String, Date> {

    private static final FastDateFormat DATE_FORMATTER = FastDateFormat.getInstance("dd.MM.yyyy hh:mm");

    @Override
    public String marshal(Date v) throws Exception {
        return DATE_FORMATTER.format(v);
    }

    @Override
    public Date unmarshal(String v) throws Exception {
        return DATE_FORMATTER.parse(v);
    }

}

