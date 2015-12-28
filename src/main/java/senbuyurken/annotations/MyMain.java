package senbuyurken.annotations;

import org.jhades.JHades;

import java.io.Serializable;

/**
 * Created by SametCokpinar on 28/12/15.
 */
public class MyMain implements Serializable {

    public static void main(String[] args) {
        new JHades().overlappingJarsReport();
    }
}
