package ar.uba.fi.cb100.clases.a2026.c02.tps.tp1;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class RegistroSobreArregloTest {

    @Test
    void creceElArreglo() {
        RegistroSobreArreglo registro = new RegistroSobreArreglo();
        for (int i = 1; i <= 20; i++) {
            registro.registrar(new Prestamo(LocalDate.of(2026, 3, 1), i, "Socio" + i, "isbn" + i, "Titulo" + i, null));
        }
        assertEquals(20, registro.cantidad());
    }

    @Test
    void padronesSinRepetidos() {
        RegistroSobreArreglo registro = new RegistroSobreArreglo();
        registro.registrar(new Prestamo(LocalDate.of(2026, 3, 1), 100, "Ana", "isbn1", "Titulo1", null));
        registro.registrar(new Prestamo(LocalDate.of(2026, 3, 2), 200, "Beto", "isbn2", "Titulo2", null));
        registro.registrar(new Prestamo(LocalDate.of(2026, 3, 3), 100, "Ana", "isbn3", "Titulo3", null));

        int[] padrones = registro.padrones();
        assertEquals(2, padrones.length);
        assertEquals(100, padrones[0]);
        assertEquals(200, padrones[1]);
    }

    @Test
    void padronInexistente() {
        RegistroSobreArreglo registro = new RegistroSobreArreglo();
        registro.registrar(new Prestamo(LocalDate.of(2026, 3, 1), 100, "Ana", "isbn1", "Titulo1", null));

        Prestamo[] resultado = registro.prestamosDe(999);
        assertEquals(0, resultado.length);
    }

    @Test
    void desempateAlfabetico() {
        RegistroSobreArreglo registro = new RegistroSobreArreglo();
        registro.registrar(new Prestamo(LocalDate.of(2026, 3, 1), 100, "Ana", "isbn1", "Beta", null));
        registro.registrar(new Prestamo(LocalDate.of(2026, 3, 2), 200, "Beto", "isbn2", "Beta", null));
        registro.registrar(new Prestamo(LocalDate.of(2026, 3, 3), 300, "Cin", "isbn3", "Alfa", null));
        registro.registrar(new Prestamo(LocalDate.of(2026, 3, 4), 400, "Dan", "isbn4", "Alfa", null));

        String[] resultado = registro.titulosMasPedidos(2);
        assertEquals("Alfa", resultado[0]);
        assertEquals("Beta", resultado[1]);
    }
}
