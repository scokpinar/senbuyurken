package senbuyurken.entities;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * User: SametCokpinar
 * Date: 21/12/14
 * Time: 18:27
 */
@XmlRootElement
public class JSONResult implements Serializable {

    Boolean result;

    public JSONResult() {
    }

    public JSONResult(Boolean result) {
        this.result = result;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "JSONResult{" +
                "result=" + result +
                '}';
    }
}
