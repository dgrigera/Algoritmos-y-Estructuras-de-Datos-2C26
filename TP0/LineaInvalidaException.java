package ar.uba.fi.cb100.clases.a2026.c02.tps.tp1;

public class LineaInvalidaException extends RuntimeException {

//ATRIBUTOS DE CLASE--------------------------------------------------------------------------------------

//ATRIBUTOS----------------------------------------------------------------------------------------------
    private final int numeroDeLinea;

//CONSTRUCTORES------------------------------------------------------------------------------------------
    public LineaInvalidaException(int numeroDeLinea, String motivo) { //constructor: recibe el numero de linea y el motivo del error
        super(motivo); //le pasa el motivo a RuntimeException que lo guarda como el mensaje de la excepcion
        this.numeroDeLinea = numeroDeLinea; //guarda el numero de linea en el atributo propio de esta clase
    }

//METODOS ABSTRACTOS--------------------------------------------------------------------------------------
//METODOS DE CLASE---------------------------------------------------------------------------------------
//METODOS GENERALES---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO-------------------------------------------------------------------------------

//GETTERS SIMPLES-----------------------------------------------------------------------------------------
    public int numeroDeLinea() { //devuelve el numero de linea guardado para que LectorDePrestamos lo use al armar el reporte de errores
        return numeroDeLinea;
    }

//SETTERS SIMPLES-----------------------------------------------------------------------------------------
}