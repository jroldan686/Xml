package jrl.acdat.xml.network;

import android.os.Environment;

import com.loopj.android.http.FileAsyncHttpResponseHandler;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import jrl.acdat.xml.model.Noticia;
import jrl.acdat.xml.utils.Analisis;
import jrl.acdat.xml.utils.FailureEvent;
import jrl.acdat.xml.utils.MessageEvent;

/**
 * Created by usuario on 11/01/18.
 */

public class DownloadTask {

    public static void executeDownload(String canal, String temp) {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), temp);
        RestClient.get(canal, new FileAsyncHttpResponseHandler(file) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                EventBus.getDefault().post(new MessageEvent("Descarga completada", file));
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                EventBus.getDefault().post(new FailureEvent("Descarga completada", FailureEvent.DOWNLOAD_ERROR));
            }
        });
    }
}
