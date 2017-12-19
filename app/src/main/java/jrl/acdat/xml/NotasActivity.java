package jrl.acdat.xml;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import jrl.acdat.xml.utils.Analisis;

public class NotasActivity extends AppCompatActivity {

    TextView txvNotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);

        txvNotas = (TextView)findViewById(R.id.txvNotas);
        try {
            txvNotas.setText(Analisis.analizarNombres(this));
        } catch (XmlPullParserException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
