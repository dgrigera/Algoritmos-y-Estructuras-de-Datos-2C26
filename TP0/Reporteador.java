package ar.uba.fi.cb100.clases.a2026.c02.tps.tp1;

import java.time.LocalDate;

public class Reporteador { //calculo el reporte de multas por socio y el ranking de titulos a partir de los prestamos ya cargados

//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
//CONSTRUCTORES -------------------------------------------------------------------------------------------
//METODOS ABSTRACTOS --------------------------------------------------------------------------------------

//METODOS DE CLASE ----------------------------------------------------------------------------------------
    public static String[] ranking(RegistroDePrestamos r, int n) {
        return r.titulosMasPedidos(n); //esto ya lo resuelve RegistroSobreArreglo aca solo lo reenvio para que Tp1 no tenga que saber de donde sale
    }

    public static FilaDeSocio[] porSocio(RegistroDePrestamos r, LocalDate corte) {
        //un mismo socio puede tener varios prestamos sueltos en r, entonces hay que juntarlos todos en una sola fila por socio
        //uso varios arreglos paralelos (misma posicion = mismo socio) porque no puedo usar HashMap
        int[] padronesUnicos = new int[r.cantidad()];
        String[] nombresSocios = new String[r.cantidad()];
        int[] cantidadPrestamos = new int[r.cantidad()];
        int[] diasAtrasoAcumulados = new int[r.cantidad()];
        int[] multasAcumuladas = new int[r.cantidad()];
        int cantidadSocios = 0;

        for (int i = 0; i < r.cantidad(); i++) { //recorro TODOS los prestamos, uno por uno
            Prestamo prestamo = r.obtener(i);
            int padronActual = prestamo.padron();

            int posicionEncontrada = -1; //busco si el padron de este prestamo ya lo venia acumulando de una vuelta anterior
            for (int j = 0; j < cantidadSocios; j++) {
                if (padronesUnicos[j] == padronActual) {
                    posicionEncontrada = j;
                }
            }

            if (posicionEncontrada == -1) { //primera vez que aparece este socio, le abro una fila nueva
                padronesUnicos[cantidadSocios] = padronActual;
                nombresSocios[cantidadSocios] = prestamo.socio();
                cantidadPrestamos[cantidadSocios] = 1;
                diasAtrasoAcumulados[cantidadSocios] = prestamo.diasDeAtraso(corte); //diasDeAtraso y multa son metodos de Prestamo, calculan usando la fecha de corte
                multasAcumuladas[cantidadSocios] = prestamo.multa(corte);
                cantidadSocios++;
            } else { //ya tenia una fila para este socio, sumo este prestamo a lo que ya llevaba acumulado
                cantidadPrestamos[posicionEncontrada]++;
                diasAtrasoAcumulados[posicionEncontrada] += prestamo.diasDeAtraso(corte);
                multasAcumuladas[posicionEncontrada] += prestamo.multa(corte);
            }
        }

        FilaDeSocio[] filas = new FilaDeSocio[cantidadSocios]; //ya tengo todo sumado por socio ahora armo las FilaDeSocio finales

        for (int i = 0; i < cantidadSocios; i++) {
            String estado;
            if (multasAcumuladas[i] > 0) {
                estado = "CON_DEUDA"; //si debe algo de multa, sea lo que sea, ya cuenta como con deuda
            } else {
                estado = "AL_DIA";
            }
            filas[i] = new FilaDeSocio(padronesUnicos[i], nombresSocios[i], cantidadPrestamos[i], diasAtrasoAcumulados[i], multasAcumuladas[i], estado);
        }

        //ordeno las filas de mayor a menor multa
        //si dos socios tienen la misma multa, gana el alfabeticamente primero (mismo criterio que use en titulosMasPedidos)
        for (int i = 0; i < cantidadSocios - 1; i++) {
            int posicionMejor = i;
            for (int j = i + 1; j < cantidadSocios; j++) {
                if (filas[j].multa() > filas[posicionMejor].multa()) {
                    posicionMejor = j;
                } else if (filas[j].multa() == filas[posicionMejor].multa() && filas[j].socio().compareTo(filas[posicionMejor].socio()) < 0) {
                    posicionMejor = j;
                }
            }
            FilaDeSocio temporal = filas[i];
            filas[i] = filas[posicionMejor];
            filas[posicionMejor] = temporal;
        }

        return filas;
    }

//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
//GETTERS SIMPLES -----------------------------------------------------------------------------------------
//SETTERS SIMPLES -----------------------------------------------------------------------------------------
}