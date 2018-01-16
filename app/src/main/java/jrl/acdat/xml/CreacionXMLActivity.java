package jrl.acdat.xml;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import jrl.acdat.xml.model.Noticia;

public class CreacionXMLActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String RSS = "http://www.alejandrosuarez.es/feed/";
    //public static final String RSS = "http://10.0.2.2/feed/alejandro.xml";
    public static final String TEMPORAL = "alejandro.xml";
    public static final String FICHERO_XML = "resultado.xml";
    Button boton;
    ArrayList<Noticia> noticias;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escribir_xml);
        boton = (Button) findViewById(R.id.boton);
        boton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v == boton)
            descarga(RSS, TEMPORAL);
    }
    private void descarga(String rss, String temporal) {

    }
}
