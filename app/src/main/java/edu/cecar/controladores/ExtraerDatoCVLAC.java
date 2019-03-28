package edu.cecar.controladores;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;

public class ExtraerDatoCVLAC {
    private ExtraerDatoCVLAC(){
    }

    private static String table = "table";

    public static Investigador  getDatos(String url) {

        Investigador investigador = null;
        try {
            //Se obtiene el documento HTML
            Document documentoHTML = Jsoup.connect(url).get();

            Element tablas = documentoHTML.select(table).get(1); //Se obtiene la primera tabla
            Element tablasInvestigacion = documentoHTML.select(table).get(7); //Se obtiene la septima tabla
            Element tablasInvestigacion2 = documentoHTML.select(table).get(6);

            Elements filasTabla = tablas.select("tr"); // Se obtienen las filas de la tabla
            Elements filaTablaInvestigacion = tablasInvestigacion.select("tr");//
            Elements filaTablaInvestigacion2 = tablasInvestigacion2.select("tr");

            int filaNombre = 0;
            int filanacionalidad = 2;
            int filaSexo =3;
            //se crea la fila investigacion

            if (filasTabla.size()>4){
                filaNombre =2;
                filanacionalidad=4;
                filaSexo=5;
            }

            //Se obtienen las columnas para cada atributo del invstigador
            String nombre = filasTabla.get(filaNombre).select("td").get(1).text();
            String nacionalidad = filasTabla.get(filanacionalidad).select("td").get(1).text();
            String sexo = filasTabla.get(filaSexo).select("td").get(1).text();

            int tam = filaTablaInvestigacion.size();
            int tam2 = filaTablaInvestigacion2.size();

            String existeInvestigacion = filaTablaInvestigacion.get(0).select("td").get(0).text();
            String existeInvestigacion2 = filaTablaInvestigacion2.get(0).select("td").get(0).text();

            String investigaciones=filaTablaInvestigacion.get(0).select("td").get(0).text();
            String investigaciones2=filaTablaInvestigacion2.get(0).select("td").get(0).text();

            System.out.printf("hhh"+nombre,nacionalidad,sexo);

            ArrayList<String> arreglo = new ArrayList<String>();
            ArrayList<String> arreglovacio = new ArrayList<String>();


            //comprobamos si existe Linea de investigacion
           if (existeInvestigacion.equals("Líneas de investigación")){
                //Se crea el objeto investigador
                for (int i = 0; i<tam; i++) {
                    arreglo.add(i,filaTablaInvestigacion.get(i).select("td").get(0).text());
                 //   System.  out.  println(arreglo.get(i));
                }

                investigador = new Investigador(nombre, nacionalidad, sexo, investigaciones,arreglo,true);

                System.out.println("gggggg"+existeInvestigacion);
            }else {
                   if (existeInvestigacion2.equals("Líneas de investigación")){
                   //Se crea el objeto investigador
                       for (int i = 0; i<tam2; i++) {
                            arreglo.add(i,filaTablaInvestigacion2.get(i).select("td").get(0).text());
                            System.out.println(arreglo.get(i));}
                            investigador = new Investigador(nombre, nacionalidad, sexo, investigaciones2,arreglo,true);
                            System.out.println("gggggg"+existeInvestigacion);
                       }
                   else {
                        investigador = new Investigador(nombre, nacionalidad, sexo,"No Posee Lineas de investigacion",arreglovacio, true);
                    }
           }
        // i
        } catch (IOException e) {
            Log.e("MYAPP", "exception: " + e.getMessage());
        }
        return investigador;
    }
}
