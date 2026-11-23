package ar.uba.fi.cb100.clases.a2026.c02.tps.tp1;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

//uso record porque Prestamo contiene datos y asi me ahorro de hacer 6 metodos
public record Prestamo(LocalDate retiro, int padron, String socio, String isbn, String titulo, LocalDate devolucion) {

//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
//CONSTRUCTORES -------------------------------------------------------------------------------------------
    public Prestamo { //constructor: se ejecuta al crear el objeto con los 5 invariantes
        if (padron <= 0) {
            throw new IllegalArgumentException("Error: padron invalido");
        }
        if (socio == null || socio.strip().isEmpty()) {
            throw new IllegalArgumentException("Error: socio invalido");
        }
        if (isbn == null || isbn.strip().isEmpty()) {
            throw new IllegalArgumentException("Error: isbn invalido");
        }
        if (titulo == null || titulo.strip().isEmpty()) {
            throw new IllegalArgumentException("Error: titulo invalido");
        }
        if (devolucion != null && devolucion.isBefore(retiro)) { //&& porque si hay prestamo pendiente no se deben comparar fechas
            throw new IllegalArgumentException("Error: se produjo devolucion antes del retiro ");
        }
    }

//METODOS ABSTRACTOS --------------------------------------------------------------------------------------
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------

    //METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
    public boolean estaPendiente() { //si lo devolvieron o no
        return devolucion == null;
    }

    public LocalDate vencimiento() {
        return retiro.plusDays(14); //calcula cuando vence el prestamo: 14 dias despues que haya sido retirado
    }

    public int diasDeAtraso(LocalDate corte) { //calcula dias de atraso. corte puede ser cualquier fecha a consultar
        LocalDate fechaLimite;
        if (estaPendiente()) {
            fechaLimite = corte; //aparece la fecha consultada porque sigue pendiente la devolucion
        } else {
            fechaLimite = devolucion; //dice la fecha en que fue devuelto
        }
        long dias = ChronoUnit.DAYS.between(vencimiento(), fechaLimite); //fechaLimite ya viene decidida por el if/else de arriba
        return (int) Math.max(0, dias); //el atraso nunca puede ser negativo
    }

    public int multa(LocalDate corte) { //calculo de la multa por atraso
        int dias = diasDeAtraso(corte);
        int monto = dias * 150;
        return Math.min(monto, 3000);
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------
//SETTERS SIMPLES -----------------------------------------------------------------------------------------
}