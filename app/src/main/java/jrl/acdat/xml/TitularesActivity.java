package jrl.acdat.xml;

import android.app.ProgressDialog;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.FileAsyncHttpResponseHandler;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;

import cz.msebera.android.httpclient.Header;
import jrl.acdat.xml.network.RestClient;
import jrl.acdat.xml.utils.Analisis;

public class TitularesActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String RSS = "https://www.alejandrosuarez.es/feed/";
    //public static final String RSS = "https://10.0.2.2/feed/alejandro.xml";
    public static final String TEMPORAL = "alejandro.xml";
    Button btnMostrar;
    TextView txvTitulares;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_titulares);
        btnMostrar = (Button) findViewById(R.id.btnMostrar);
        btnMostrar.setOnClickListener(this);
        txvTitulares = (TextView) findViewById(R.id.txvTitulares);
    }

    @Override
    public void onClick(View v) {
        if (v == btnMostrar)
            descarga(RSS, TEMPORAL);
    }

    private void descarga(String rss, String temporal) {
        final ProgressDialog progreso = new ProgressDialog(this);
        File miFichero = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), temporal);

        RestClient.get(rss, new FileAsyncHttpResponseHandler(miFichero) {

            @Override
            public void onStart() {
                super.onStart();
                progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progreso.setMessage("Conectando . . .");
                progreso.setCancelable(false);
                progreso.show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                progreso.dismiss();
                Toast.makeText(getApplicationContext(), "Fichero no descargado", Toast.LENGTH_SHORT).show();
                txvTitulares.setText("Error: " + throwable.getMessage());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                progreso.dismiss();
                Toast.makeText(getApplicationContext(), "Fichero descargado correctamente", Toast.LENGTH_SHORT).show();
                try {
                    txvTitulares.setText(Analisis.analizarRSS(file));
                } catch (XmlPullParserException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
