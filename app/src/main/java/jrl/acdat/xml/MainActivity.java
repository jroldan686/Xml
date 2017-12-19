package jrl.acdat.xml;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnPartes, btnNotas, btnNoticias, btnTitulares, btnCreacionXML;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPartes = (Button) findViewById(R.id.btnPartes);
        btnPartes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(MainActivity.this, PartesActivity.class);
                startActivity(i);
            }
        });
        btnNotas = (Button) findViewById(R.id.btnNotas);
        btnNotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(MainActivity.this, NotasActivity.class);
                startActivity(i);
            }
        });
        btnNoticias = (Button) findViewById(R.id.btnNoticias);
        btnNoticias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(MainActivity.this, NoticiasActivity.class);
                startActivity(i);
            }
        });
        btnTitulares = (Button) findViewById(R.id.btnTitulares);
        btnTitulares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(MainActivity.this, TitularesActivity.class);
                startActivity(i);
            }
        });
        btnCreacionXML = (Button) findViewById(R.id.btnCreacionXML);
        btnCreacionXML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(MainActivity.this, CreacionXMLActivity.class);
                startActivity(i);
            }
        });
    }
}
