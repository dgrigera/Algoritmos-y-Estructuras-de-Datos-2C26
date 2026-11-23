package ar.uba.fi.cb100.clases.a2026.c02.tps.tp1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Arrays;

//leo el csv linea por linea y armo el registro. si una linea esta mal no rompo todo, la descarto y sigo
public class LectorDePrestamos {

//ATRIBUTOS DE CLASE--------------------------------------------------------------------------------------
//ATRIBUTOS----------------------------------------------------------------------------------------------
//CONSTRUCTORES------------------------------------------------------------------------------------------
//METODOS ABSTRACTOS--------------------------------------------------------------------------------------

//METODOS DE CLASE----------------------------------------------------------------------------------------
    public static ResultadoDeCarga cargar(Path archivo) throws IOException {

        String[] lineas = Files.readAllLines(archivo).toArray(new String[0]); //cada posicion es una linea del archivo
        RegistroDePrestamos registro = new RegistroSobreArreglo(); //la misma caja que use antes, aca voy metiendo los prestamos que esten bien
        String[] errores = new String[lineas.length]; //por las dudas que me fallen todas las lineas
        int cantidadDeErrores = 0;
        int lineasDeDatos = 0; //cuento solo las lineas que tienen datos, sin contar comentarios ni blancos

        for (int posicion = 0; posicion < lineas.length; posicion++) {
            int numeroDeLinea = posicion + 1; //cuento el archivo desde la linea 1, no desde 0 como el arreglo
            String linea = lineas[posicion];

            if (linea.strip().isEmpty() || linea.strip().startsWith("#")) {
                //blanco o comentario del archivo que empieza con #
            } else {
                lineasDeDatos++; //de aca en mas es una linea real, con datos de un prestamo

                try {
                    String[] campos = linea.split(";", -1); //separo por ; . el -1 es importante, sino java me come el ultimo campo si esta vacio

                    if (campos.length != 6) { //tienen que ser 6 campos siempre, si no algo esta mal escrito
                        throw new LineaInvalidaException(numeroDeLinea, "se esperaban 6 campos y llegaron " + campos.length); //mi excepcion propia, guarda tambien el numero de linea
                    }

                    LocalDate retiro = LocalDate.parse(campos[0].strip()); //si la fecha esta mal escrita esto tira excepcion solo
                    int padron = Integer.parseInt(campos[1].strip()); //si no es numero tambien tira excepcion sola
                    String socio = campos[2];
                    String isbn = campos[3];
                    String titulo = campos[4];
                    LocalDate devolucion;

                    if (campos[5].strip().isEmpty()) {
                        devolucion = null; //vacio quiere decir que todavia no lo devolvio, esta pendiente
                    } else {
                        devolucion = LocalDate.parse(campos[5].strip());
                    }

                    Prestamo prestamo = new Prestamo(retiro, padron, socio, isbn, titulo, devolucion); //aca uso el constructor de Prestamo, que valida todo de nuevo con sus 5 reglas
                    registro.registrar(prestamo); //mismo registrar que ya use en RegistroSobreArreglo

                } catch (RuntimeException e) { //agarra cualquier falla de arriba
                    errores[cantidadDeErrores] = "linea " + numeroDeLinea + ": " + e.getMessage();
                    cantidadDeErrores++;
                }
            }
        }
        String[] erroresFinales = Arrays.copyOf(errores, cantidadDeErrores); //recorto el arreglo al tamaño real de errores que hubo
        return new ResultadoDeCarga(registro, erroresFinales, lineasDeDatos); //empaqueto todo junto: el mismo record ResultadoDeCarga que arme antes
    }

//METODOS GENERALES---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO-------------------------------------------------------------------------------
//GETTERS SIMPLES-----------------------------------------------------------------------------------------
//SETTERS SIMPLES----------------------------------------------------------------------------------------
}