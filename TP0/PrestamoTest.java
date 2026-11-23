package ar.uba.fi.cb100.clases.a2026.c02.tps.tp1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class PrestamoTest {
    @Test
    void testPadronInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Prestamo(LocalDate.now(), -5, "Ana Gomez", "123", "Titulo", null);
        });
    }

    @Test
    void testSocioInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Prestamo(LocalDate.now(), 41234, null, "123", "Titulo", null);
        });
    }

    @Test
    void testIsbnInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Prestamo(LocalDate.now(), 41234, "Ana Gomez", null, "Titulo", null);
        });
    }

    @Test
    void testTituloInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Prestamo(LocalDate.now(), 41234, "Ana Gomez", "123", null, null);
        });
    }

    @Test
    void testDevolucionAntesDelRetiro() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Prestamo(LocalDate.of(2026, 4, 15), 41234, "Ana Gomez", "123", "Titulo", LocalDate.of(2026, 4, 14));
        });
    }

    @Test
    void testVencimiento() {
        Prestamo p = new Prestamo(LocalDate.of(2026, 3, 2), 41234, "Ana Gomez", "123", "Titulo", null);
        assertEquals(LocalDate.of(2026, 3, 16), p.vencimiento());
    }

    @Test
    void testSinAtraso() {
        Prestamo p = new Prestamo(LocalDate.of(2026, 3, 2), 41234, "Ana Gomez", "123", "Titulo", LocalDate.of(2026, 3, 16));
        assertEquals(0, p.diasDeAtraso(LocalDate.of(2026, 5, 4)));
    }

    @Test
    void testAtrasoPendiente() {
        Prestamo p = new Prestamo(LocalDate.of(2026, 3, 2), 41234, "Ana Gomez", "123", "Titulo", null);
        assertEquals(49, p.diasDeAtraso(LocalDate.of(2026, 5, 4)));
    }

    @Test
    void testTopeDeMulta() {
        Prestamo p = new Prestamo(LocalDate.of(2026, 3, 2), 41234, "Ana Gomez", "123", "Titulo", null);
        assertEquals(3000, p.multa(LocalDate.of(2026, 5, 4)));
    }
}