package ar.uba.fi.cb100.clases.a2026.c02.tps.tp1;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.nio.file.Files;

//junta todo lo demas: lee el archivo, calculo los reportes y los escribo
public class Tp1 {

    //ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
    private static final String ENTRADA_POR_DEFECTO = "src/main/java/ar/uba/fi/cb100/clases/a2026/c02/tps/tp1/datos/prestamos.csv"; //ruta relativa
    private static final LocalDate CORTE_POR_DEFECTO = LocalDate.parse("2026-05-04"); //fecha de corte que pide el enunciado

//ATRIBUTOS -----------------------------------------------------------------------------------------------
//CONSTRUCTORES -------------------------------------------------------------------------------------------
//METODOS ABSTRACTOS --------------------------------------------------------------------------------------

//METODOS DE CLASE ----------------------------------------------------------------------------------------
    public static void main(String[] args) throws IOException {
        Path archivo; //aca voy a guardar el archivo que finalmente uso sea el que me pasen o el de por defecto

        if (args.length > 0) { //args son los argumentos que le pasan al programa por consola, si hay al menos uno
            archivo = Path.of(args[0]); //uso ese como archivo de entrada
        } else {
            archivo = Path.of(ENTRADA_POR_DEFECTO); //sino, uso el de ejemplo
        }

        LocalDate corte; //lo mismo pero con la fecha de corte

        if (args.length > 1) { //si me pasaron un segundo argumento
            corte = LocalDate.parse(args[1]); //lo uso como fecha de corte
        } else {
            corte = CORTE_POR_DEFECTO;
        }

        ResultadoDeCarga resultado = LectorDePrestamos.cargar(archivo); //leo el archivo, mismo metodo que ya vimos, me devuelve registro + errores + total de lineas
        System.out.println("Lineas de datos: " + resultado.lineasDeDatos() + " | validas: " + resultado.registro().cantidad() + " | descartadas: " + resultado.errores().length); //resumen que pide el enunciado

        for (int i = 0; i < resultado.errores().length; i++) { //imprimo cada error encontrado, uno por linea, ya vienen armados con el numero de linea desde LectorDePrestamos
            System.out.println("  " + resultado.errores()[i]);
        }

        FilaDeSocio[] filas = Reporteador.porSocio(resultado.registro(), corte); //mismo Reporteador que ya vimos, calcula la multa de cada socio a esta fecha de corte
        String[] ranking = Reporteador.ranking(resultado.registro(), 3); //y el ranking de los 3 titulos mas pedidos

        Path destinoCsv = Path.of("reporte.csv"); //el csv se crea en la carpeta donde este parado el programa al correr (el working directory)
        new ExportadorCsv().exportar(filas, destinoCsv); //escribo el csv, mismo exportador que ya vimos, con las filas ya calculadas

        Path destinoTxt = Path.of("reporte.txt");
        Files.writeString(destinoTxt, "BIBLIOTECA FIUBA - REPORTE DE MULTAS\nFecha de corte: " + corte + "\n\n"); //escribo yo el encabezado primero, sin APPEND, asi arranca el archivo de cero
        new ExportadorTxt().exportar(filas, destinoTxt); //y ExportadorTxt agrega la tabla despues, por eso el usa APPEND y no me pisa este encabezado

        StringBuilder textoRanking = new StringBuilder(); //aca armo aparte el texto del ranking, para agregarlo al final del mismo txt
        textoRanking.append("\nTITULOS MAS PEDIDOS\n");

        for (int i = 0; i < ranking.length; i++) { //recorro los 3 titulos que me dio el ranking
            int contador = 0; //arranca en 0 para cada titulo, cuenta cuantas veces aparece ESTE titulo puntual

            for (int j = 0; j < resultado.registro().cantidad(); j++) { //recorro TODOS los prestamos otra vez, porque titulosMasPedidos me da el nombre pero no cuantas veces se pidio
                if (resultado.registro().obtener(j).titulo().equals(ranking[i])) { //si el prestamo j tiene el mismo titulo que estoy contando
                    contador = contador + 1;
                }
            }
            textoRanking.append(" ").append(i + 1).append(". ").append(ranking[i]).append(" ").append(contador).append("\n"); //arma la linea "1. Titulo   6"
        }

        Files.writeString(destinoTxt, textoRanking.toString(), java.nio.file.StandardOpenOption.APPEND); //agrego el ranking al final del mismo archivo txt, sin borrar la tabla de arriba
    }

//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
//GETTERS SIMPLES -----------------------------------------------------------------------------------------
//SETTERS SIMPLES -----------------------------------------------------------------------------------------
}