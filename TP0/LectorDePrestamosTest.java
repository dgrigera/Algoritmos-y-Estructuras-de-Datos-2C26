package ar.uba.fi.cb100.clases.a2026.c02.tps.tp1;

import org.junit.jupiter.api.Test;
import java.nio.file.Path;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class LectorDePrestamosTest {

    @Test
    void lineasValidasYErrores() throws IOException {
        Path archivo = Path.of("src/main/java/ar/uba/fi/cb100/clases/a2026/c02/tps/tp1/datos/prestamos.csv");
        ResultadoDeCarga resultado = LectorDePrestamos.cargar(archivo);

        assertEquals(18, resultado.registro().cantidad());
        assertEquals(4, resultado.errores().length);
    }
}
