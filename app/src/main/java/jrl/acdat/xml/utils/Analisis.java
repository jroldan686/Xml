package jrl.acdat.xml.utils;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import jrl.acdat.xml.R;
import jrl.acdat.xml.model.Noticia;

/**
 * Created by usuario on 12/12/17.
 */

public class Analisis {

    public static String analizar(String texto) throws XmlPullParserException, IOException
    {
        StringBuilder cadena = new StringBuilder();
        XmlPullParser xpp = Xml.newPullParser();
        xpp.setInput( new StringReader(texto));
        int eventType = xpp.getEventType();
        cadena.append("Inicio . . . \n");
        while (eventType != XmlPullParser. END_DOCUMENT ) {

            if(eventType == XmlPullParser.START_DOCUMENT) cadena.append("START DOCUMENT").append("\n");
            if(eventType == XmlPullParser.START_TAG) cadena.append("START TAG: " ).append(xpp.getName()).append("\n" );
            if(eventType == XmlPullParser.TEXT) cadena.append("TEXT: " ).append(xpp.getText()).append("\n" );
            if(eventType == XmlPullParser.END_TAG) cadena.append("END TAG: " ).append(xpp.getName()).append("\n" );

        }
        //System.out.println("End document");
        cadena.append("End document" + "\n" + "Fin");
        return cadena.toString();
    }

    public static String analizarNombres(Context c) throws XmlPullParserException, IOException {
        boolean esNombre = false;
        boolean esNota = false;
        StringBuilder cadena = new StringBuilder();
        Double suma = 0.0;
        int contador = 0;
        XmlResourceParser xrp = c.getResources().getXml(R.xml.alumnos);
        int eventType = xrp.getEventType();
        while (eventType != XmlPullParser. END_DOCUMENT ) {
            switch (eventType) {
                case XmlPullParser.START_TAG :
                    if (xrp.getName().equals("nombre")) {
                        esNombre = true;
                    }
                    if (xrp.getName().equals("nota")) {
                        esNota = true;
                        for(int i = 0; i < xrp.getAttributeCount(); i++) {
                            cadena.append(xrp.getAttributeName(i).substring(0, 1).toUpperCase() +
                                          xrp.getAttributeName(i).substring(1) + ": " +
                                          xrp.getAttributeValue(i) + "\n");
                            /*
                            if(xrp.getAttributeName(i).equals("asignatura")) {
                                cadena.append("Asignatura: " + xrp.getAttributeValue(i) + "\n");
                            }
                            if(xrp.getAttributeName(i).equals("fecha")) {
                                cadena.append("Fecha: " + xrp.getAttributeValue(i) + "\n");
                            }
                            */
                        }
                    }
                    break;
                case XmlPullParser.TEXT :
                    if (esNombre) {
                        cadena.append("Alumno: " + xrp.getText() + "\n");
                    }
                    if (esNota) {
                        cadena.append("Nota: " + xrp.getText() + "\n\n");
                        suma += Double.valueOf(xrp.getText());
                        contador++;
                    }
                    break;
                case XmlPullParser.END_TAG :
                    if (xrp.getName().equals("nombre")) {
                        esNombre = false;
                    }
                    if (xrp.getName().equals("nota")) {
                        esNota = false;
                    }
                    break;
            }
            eventType = xrp.next();
        }
        cadena.append("Nota media: " +  String.format("%.2f", suma / contador));
        xrp.close();
        return cadena.toString();
    }

    public static String analizarRSS(File file) throws NullPointerException, XmlPullParserException,
            IOException {
        boolean dentroItem = false;
        boolean dentroTitle = false;
        StringBuilder builder = new StringBuilder();
        XmlPullParser xpp = Xml.newPullParser();

        xpp.setInput(new FileReader(file));
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if(xpp.getName().equalsIgnoreCase("item"))
                        dentroItem = true;
                    if(dentroItem && xpp.getName().equalsIgnoreCase("title"))
                        dentroTitle = true;
                    break;
                case XmlPullParser.TEXT:
                    if(dentroTitle)
                        builder.append(xpp.getText() + "\n");
                    break;
                case XmlPullParser.END_TAG:
                    if(xpp.getName().equalsIgnoreCase("item"))
                        dentroItem = false;
                    if(dentroItem && xpp.getName().equalsIgnoreCase("title"))
                        dentroTitle = false;
                    break;
            }
            eventType = xpp.next();
        }
        return builder.toString();
    }

    public static ArrayList<Noticia> analizarNoticias(File file) throws XmlPullParserException, IOException {
        int eventType;
        ArrayList<Noticia> noticias = null;
        Noticia actual = null;
        boolean dentroItem = false;
        boolean dentroTitle = false;
        boolean dentroLink = false;
        XmlPullParser xpp = Xml.newPullParser();
        xpp.setInput(new FileReader(file));
        eventType=xpp.getEventType();
        while (eventType!=XmlPullParser.END_DOCUMENT){
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:

                    break;
                case XmlPullParser.START_TAG:
                    if(xpp.getName().equalsIgnoreCase("item")) {
                        dentroItem = true;
                        actual = new Noticia();
                    }
                    if(dentroItem && xpp.getName().equalsIgnoreCase("title"))
                        dentroTitle = true;
                    if(dentroItem && xpp.getName().equalsIgnoreCase("link"))
                        dentroLink = true;
                    break;
                case XmlPullParser.TEXT:
                    if(dentroTitle)
                        actual.setTitle(xpp.getText());
                    if(dentroLink)
                        actual.setLink(xpp.getText());
                    break;
                case XmlPullParser.END_TAG:
                    if(xpp.getName().equalsIgnoreCase("item")) {
                        dentroItem = false;
                        noticias.add(actual);
                    }
                    if(dentroItem && xpp.getName().equalsIgnoreCase("title"))
                        dentroTitle = false;
                    if(dentroItem && xpp.getName().equalsIgnoreCase("link"))
                        dentroLink = false;
                    break;
            }
            eventType = xpp.next();
        }
        //devolver el array de noticias
        return noticias;
    }
}
