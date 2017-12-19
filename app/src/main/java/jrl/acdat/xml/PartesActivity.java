package jrl.acdat.xml;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import jrl.acdat.xml.utils.Analisis;

public class PartesActivity extends AppCompatActivity {

    public static final String TEXTO = "<texto><uno>Hello World!\n" +
            "</uno><dos>Goodbye</dos></texto>";
    private TextView txvInformacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partes);

        txvInformacion = (TextView)findViewById(R.id.txvInformacion);

        try {
            txvInformacion.setText(Analisis.analizar(TEXTO));
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            txvInformacion.setText(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            txvInformacion.setText(e.getMessage());
        }
    }
}
