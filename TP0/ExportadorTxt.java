package ar.uba.fi.cb100.clases.a2026.c02.tps.tp1;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;

//reporte en formato texto con columnas y los totales al final
public class ExportadorTxt implements ExportadorDeReporte {

//ATRIBUTOS DE CLASE--------------------------------------------------------------------------------------
//ATRIBUTOS-----------------------------------------------------------------------------------------------
//CONSTRUCTORES-------------------------------------------------------------------------------------------
//METODOS ABSTRACTOS--------------------------------------------------------------------------------------
//METODOS DE CLASE----------------------------------------------------------------------------------------
//METODOS GENERALES---------------------------------------------------------------------------------------

//METODOS DE COMPORTAMIENTO-------------------------------------------------------------------------------
    @Override
    public void exportar(FilaDeSocio[] filas, Path destino) throws IOException {

        StringBuilder texto = new StringBuilder(); //se va armando el texto aca antes de escribirlo
        texto.append(String.format("%-6s %-15s %-9s %-10s %6s %s%n", "Padron", "Socio", "Prestamos", "DiasAtraso", "Multa", "Estado")); //encabezado de la tabla
        texto.append("-".repeat(70)).append("\n"); //linea separadora repito el guion 70 veces para que quede del ancho de la tabla

        int totalPrestamos = 0; //estos 3 arrancan en 0 antes del for porque sino se resetean en cada vuelta
        int totalDiasAtraso = 0;
        int totalMulta = 0;

        for (int i = 0; i < filas.length; i++) { //recorro cada FilaDeSocio ya calculada por Reporteador.porSocio
            FilaDeSocio fila = filas[i];
            texto.append(String.format("%-6d %-15s %-9d %-10d %6d %s%n", fila.padron(), fila.socio(), fila.prestamos(), fila.diasDeAtraso(), fila.multa(), fila.estado())); //%-Nd es numero, %6d sin el guion es numero alineado a la derecha (para la multa)
            totalPrestamos += fila.prestamos(); //voy acumulando los totales de cada socio mientras escribo su fila
            totalDiasAtraso += fila.diasDeAtraso();
            totalMulta += fila.multa();
        }

        texto.append("-".repeat(70)).append("\n"); //otra linea separadora antes de los totales
        texto.append(String.format("%-6s %-15s %-9d %-10d %6d%n", "TOTALES", "", totalPrestamos, totalDiasAtraso, totalMulta)); //fila final con la suma de todos los socios, sin estado

        Files.writeString(destino, texto.toString(), java.nio.file.StandardOpenOption.APPEND); //APPEND porque Tp1 ya escribio el encabezado del reporte antes de llamarme a mi
    }

//GETTERS SIMPLES-----------------------------------------------------------------------------------------
    @Override
    public String extension() {
        return "txt"; //asi Tp1 sabe con que extension nombrar el archivo si hiciera falta
    }

//SETTERS SIMPLES-----------------------------------------------------------------------------------------
}