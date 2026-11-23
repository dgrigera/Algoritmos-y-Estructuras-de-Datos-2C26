package ar.uba.fi.cb100.clases.a2026.c02.tps.tp1;


//cualquier clase que quiera ser esta interfaz debe tener estos metodos
public interface RegistroDePrestamos {
    void registrar(Prestamo p); //guarda el prestamo p adentro del arreglo interno (privado) de RegistroSobreArreglo
    int cantidad(); //dice cuantos prestamos hay guardados en el arreglo interno sin contar espacio
    Prestamo obtener(int i); //devuelve el prestamo guardado en la posicion i del arreglo interno
    int[] padrones(); //devuelve los padrones distintos que hay en el arreglo interno cada uno una sola vez
    Prestamo[] prestamosDe(int padron); //devuelve todos los prestamos de ese padron o un arreglo vacio si no hay
    String[] titulosMasPedidos(int n); //devuelve los n titulos mas pedidos ordenados de mayor a menor y con desempate alfabetico
}
