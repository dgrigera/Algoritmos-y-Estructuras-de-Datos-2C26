package ar.uba.fi.cb100.clases.a2026.c02.tps.tp1;

import java.util.Arrays;

//clase que cumple el contrato de RegistroDePrestamos
//rol de los metodos explicados en la interfaz
//recordar que un prestamo contiene retiro, padron, socio, isbn, titulo, devolucion
public class RegistroSobreArreglo implements RegistroDePrestamos {

//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
    //solo se modifica desde adentro de la clase
    private Prestamo[] prestamosRegistrados = new Prestamo[8]; //arreglo interno que guarda objetos del tipo Prestamo
    private int cantidadUsada = 0; //cuantos de los 8 casilleros estan realmente ocupados por un prestamo

//CONSTRUCTORES -------------------------------------------------------------------------------------------
//METODOS ABSTRACTOS --------------------------------------------------------------------------------------
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------

    //METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
    //override marca los metodos que cumplen el contrato de la interfaz
    @Override
    public void registrar(Prestamo prestamo) {
        if (cantidadUsada == prestamosRegistrados.length) { //si no hay lugar se duplica la cantidad del arreglo
            prestamosRegistrados = Arrays.copyOf(prestamosRegistrados, prestamosRegistrados.length * 2); //si es asi crea un arreglo nuevo del doble de tamaño
        }
        prestamosRegistrados[cantidadUsada] = prestamo; //se guarda el prestamo en la primer posicion libre
        cantidadUsada++; //actualiza por prestamo guardado
    }

    @Override
    public Prestamo obtener(int i) { //devuelve el prestamo de la posicion i, valida antes que el indice tenga sentido
        if (i < 0 || i >= cantidadUsada) {
            throw new IndexOutOfBoundsException(i + " es un indice invalido");
        }
        return prestamosRegistrados[i];
    }

    @Override
    public int[] padrones() { //arreglo de los padrones sin repetidos en orden en que aparecieron los prestamos
        int [] temporalPadrones = new int[cantidadUsada]; //heredado del estado que dejo el metodo registrar
        int cantidadUnicos = 0;

        for (int i = 0; i < cantidadUsada; i++) { //recorre lo que ya se guardo con registrar hasta cantidadUsada
            int padronActual = prestamosRegistrados[i].padron(); //lee el padron del prestamo que ya esta guardado en el arreglo interno
            boolean yaExiste = false;

            for (int j = 0; j < cantidadUnicos; j++) { //recorre solo los padrones unicos encontrados hasta ahora en este metodo o sea no en el arreglo interno
                if (temporalPadrones[j] == padronActual){
                    yaExiste = true;
                }
            }

            if (yaExiste == false) { //si no esta el padron antes, es la primera vez que aparece
                temporalPadrones[cantidadUnicos] = padronActual; //se agrega en la proxima posicion libre
                cantidadUnicos++;
            }
        }
        return Arrays.copyOf(temporalPadrones, cantidadUnicos); //recorta al tamaño justo de padrones unicos encontrados
    }

    @Override
    public Prestamo[] prestamosDe(int padron) { //devuelve todos los prestamos de ese padron, o un arreglo vacio si no hay ninguno
        Prestamo[] temporalPrestamo = new Prestamo[cantidadUsada]; //tamaño maximo posible, por si todos los prestamos fueran de este padron (heredado del estado que dejo registrar)
        int cantidadPrestamos = 0; //cuantos prestamos coinciden con el padron buscado

        for (int i = 0; i < cantidadUsada; i++) { //recorre lo que ya se guardo con registrar
            if (prestamosRegistrados[i].padron() == padron) { //compara el padron de este prestamo (esta vuelta) contra el que se busca
                temporalPrestamo[cantidadPrestamos] = prestamosRegistrados[i]; //si coincide se guarda el objeto en la prox posicion libre del arreglo temporal
                cantidadPrestamos++;
            }
        }
        return Arrays.copyOf(temporalPrestamo, cantidadPrestamos); //arreglo con el tamaño justo
    }

    @Override
    public String[] titulosMasPedidos(int n) { //con desempate alfabetico
        String[] titulosUnicos = new String[cantidadUsada]; //tamaño maximo posible, tantos titulos distintos como prestamos haya (heredado del estado que dejo registrar)
        int[] conteos = new int[cantidadUsada]; //guarda cuantas veces se pidio cada titulo en la misma posicion que su titulo en titulosUnicos
        int cantidadTitulos = 0; //contador titulos distintos encontrados hasta ahora

        for (int i = 0; i < cantidadUsada; i++) { //recorre todos los prestamos para contar cuantas veces se pidio el titulo
            String tituloActual = prestamosRegistrados[i].titulo();

            int posicionEncontrada = -1; //el -1 quiere decir que todavia no se encontro el titulo
            for (int j = 0; j < cantidadTitulos; j++) { //recorre solo los titulos unicos encontrados hasta ahora en este metodo, no el arreglo interno
                if (titulosUnicos[j].equals(tituloActual)) {
                    posicionEncontrada = j; //guarda en que posicion esta para despues sumarle uno al conteo de esa posicion
                }
            }
            if (posicionEncontrada == -1) {
                titulosUnicos[cantidadTitulos] = tituloActual; //lo agrega en la prox posicion libre
                conteos[cantidadTitulos] = 1; //1 porque es la primera vez que aparece
                cantidadTitulos++;
            } else { //ya estaba guardado de una vuelta anterior
                conteos[posicionEncontrada]++; //se le suma uno a su conteo existente
            }
        }

        for (int i = 0; i < cantidadTitulos - 1; i++) { //recorre cada posicion del arreglo que armo recien (titulosUnicos y conteos) para ir ordenando de mayor a menor
            int posicionMejor = i; //arranca asumiendo que la posicion actual ya es la mejor candidata

            for (int j = i + 1; j < cantidadTitulos; j++) { //busca entre lo que falta ordenar si hay un candidato mejor que el actual
                if (conteos[j] > conteos[posicionMejor]) { //si este titulo fue pedido mas veces que el mejor candidato hasta ahora
                    posicionMejor = j;

                } else if (conteos[j] == conteos[posicionMejor] && titulosUnicos[j].compareTo(titulosUnicos[posicionMejor]) < 0) { //gana el menor alfabeticamente
                    posicionMejor = j;

                }
            }
            String tituloTemporal = titulosUnicos[i]; //guarda el titulo antes de pisarlo asi no se pierde en el intercambio
            titulosUnicos[i] = titulosUnicos[posicionMejor]; //trae el mejor candidato a la posicion i
            titulosUnicos[posicionMejor] = tituloTemporal; //manda el que estaba en i al lugar del que se movio

            int conteoTemporal = conteos[i]; //mismo intercambio pero para el conteo en paralelo con titulosUnicos
            conteos[i] = conteos[posicionMejor];
            conteos[posicionMejor] = conteoTemporal;

        }
        int cantidadAResultar = Math.min(n, cantidadTitulos); //no pido mas titulos de los que realmente existen
        return Arrays.copyOf(titulosUnicos, cantidadAResultar); //arreglo final ya ordenado y del tamaño justo
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------
    @Override
    public int cantidad() { //cuantos prestamos hay guardados de verdad, no el tamaño total del arreglo
        return cantidadUsada;
    }

//SETTERS SIMPLES -----------------------------------------------------------------------------------------
}