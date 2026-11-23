package ar.uba.fi.cb100.clases.a2026.c02.tps.tp1;

import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ExportadorCsvTest {

    @Test
    void archivoCsvCorrecto() throws IOException {
        FilaDeSocio fila = new FilaDeSocio(100, "Ana", 3, 5, 750, "CON_DEUDA");
        FilaDeSocio[] filas = {fila};

        Path archivoTemporal = Files.createTempFile("reporte", ".csv");
        ExportadorCsv exportador = new ExportadorCsv();
        exportador.exportar(filas, archivoTemporal);

        List<String> lineas = Files.readAllLines(archivoTemporal);

        assertEquals("padron;socio;prestamos;dias_atraso;multa;estado", lineas.get(0));
        assertEquals("100;Ana;3;5;750;CON_DEUDA", lineas.get(1));
    }
}