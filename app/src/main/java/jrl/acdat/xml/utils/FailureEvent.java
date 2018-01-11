package jrl.acdat.xml.utils;

/**
 * Created by usuario on 11/01/18.
 */

public class FailureEvent {

    public static final int DOWNLOAD_ERROR = 1;

    public final String message;
    public final int statusCode;

    public FailureEvent(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

}
