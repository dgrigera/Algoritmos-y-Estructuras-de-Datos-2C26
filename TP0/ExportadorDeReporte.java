package ar.uba.fi.cb100.clases.a2026.c02.tps.tp1;

import java.io.IOException;
import java.nio.file.Path;

public interface ExportadorDeReporte {
    void exportar(FilaDeSocio[] filas, Path destino) throws IOException; //recibe las filas del reporte, donde guardarlas y escribe el archivo
    String extension(); //dice si el archivo es txt o csv

}



