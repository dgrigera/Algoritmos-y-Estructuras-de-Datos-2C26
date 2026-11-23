package ar.uba.fi.cb100.clases.a2026.c02.tps.tp1;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;

//escribo el reporte en formato csv uno de los dos exportadores que pide el contrato ExportadorDeReporte
public class ExportadorCsv implements ExportadorDeReporte {

//ATRIBUTOS DE CLASE--------------------------------------------------------------------------------------
//ATRIBUTOS----------------------------------------------------------------------------------------------
//CONSTRUCTORES-------------------------------------------------------------------------------------------
//METODOS ABSTRACTOS--------------------------------------------------------------------------------------
//METODOS DE CLASE----------------------------------------------------------------------------------------
//METODOS GENERALES---------------------------------------------------------------------------------------

//METODOS DE COMPORTAMIENTO-------------------------------------------------------------------------------
    @Override
    public void exportar(FilaDeSocio[] filas, Path destino) throws IOException {
        StringBuilder dato = new StringBuilder(); //voy armando el texto completo aca antes de escribirlo de una sola vez

        dato.append("padron;socio;prestamos;dias_atraso;multa;estado\n"); //encabezado

        for (int posicion = 0; posicion < filas.length; posicion++) { //recorro cada fila que ya calculo Reporteador.porSocio
            FilaDeSocio fila = filas[posicion];
            dato.append(fila.padron() + ";" + fila.socio() + ";" + fila.prestamos() + ";" + fila.diasDeAtraso() + ";" + fila.multa() + ";" + fila.estado() + "\n"); //pego los 6 datos separados por ; como pide el formato csv
        }
        Files.writeString(destino, dato.toString()); //escribo todo junto en el archivo de destino
    }

//GETTERS SIMPLES----------------------------------------------------------------------------------------
    @Override
    public String extension() {
        return "csv"; //asi Tp1 sabe con que extension nombrar el archivo si hiciera falta
    }

//SETTERS SIMPLES----------------------------------------------------------------------------------------
}