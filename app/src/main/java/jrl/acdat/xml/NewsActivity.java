package jrl.acdat.xml;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import jrl.acdat.xml.model.Noticia;
import jrl.acdat.xml.network.DownloadTask;
import jrl.acdat.xml.utils.Analisis;
import jrl.acdat.xml.utils.FailureEvent;
import jrl.acdat.xml.utils.MessageEvent;

public class NewsActivity extends AppCompatActivity {

    public static final String SITE = "http://www.europapress.es/rss/rss.aspx?ch=00279";
    public static final String PATH = "noticias.txt";

    ListView listView;
    ArrayList<Noticia> noticias;
    ArrayAdapter<Noticia> adapter;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias);
        dialog = new ProgressDialog(this);
        noticias = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listView = findViewById(android.R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(NewsActivity.this, WebActivity.class);
                intent.putExtra("url", noticias.get(i).getUrl());
                startActivity(intent);
            }
        });
        listView.setAdapter(adapter);
    }

    public void onClick(View view) {
        dialog.show();
        DownloadTask.executeDownload(SITE, PATH);
    }

    // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        Toast.makeText(this, event.message, Toast.LENGTH_SHORT).show();
        dialog.dismiss();
        try {
            noticias = Analisis.analizarNoticias(event.file);
            adapter.clear();
            adapter.addAll(noticias);
        } catch (XmlPullParserException | IOException e) {
            Toast.makeText(this, "Error: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // This method will be called when a SomeOtherEvent is posted
    @Subscribe
    public void onFailureEvent(FailureEvent event) {
        dialog.dismiss();
        switch (event.statusCode) {
            case FailureEvent.DOWNLOAD_ERROR:
                Toast.makeText(this, event.message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
