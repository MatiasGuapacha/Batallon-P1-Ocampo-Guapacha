package co.edu.uniquindio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VehiculoMilitarTest {

    private TransporteTropas transporte;
    private Blindado blindado;
    private Apoyo apoyo;

    @BeforeEach
    void setUp() {
        transporte = new TransporteTropas("T-01", "Camion de tropas", 2020,
                12000, EstadoOperativo.DISPONIBLE, 25);
        blindado = new Blindado("B-01", "Vehiculo blindado", 2021,
                8000, EstadoOperativo.DISPONIBLE, 4);
        apoyo = new Apoyo("A-01", "Ambulancia", 2022,
                5000, EstadoOperativo.DISPONIBLE, TipoFuncion.MEDICO);
    }


    @Test
    void crearTransporteTropas_almacenaAtributosGeneralesYParticulares() {
        assertNotNull(transporte);
        assertEquals("T-01", transporte.getId());
        assertEquals("Camion de tropas", transporte.getModelo());
        assertEquals(2020, transporte.getAnioFabricacion());
        assertEquals(12000, transporte.getKilometraje(), 0.001);
        assertEquals(EstadoOperativo.DISPONIBLE, transporte.getEstadoOperativo());
        assertEquals(25, transporte.getCapacidadSoldados());
    }

    @Test
    void crearBlindado_almacenaAtributosGeneralesYParticulares() {
        assertNotNull(blindado);
        assertEquals("B-01", blindado.getId());
        assertEquals("Vehiculo blindado", blindado.getModelo());
        assertEquals(2021, blindado.getAnioFabricacion());
        assertEquals(8000, blindado.getKilometraje(), 0.001);
        assertEquals(EstadoOperativo.DISPONIBLE, blindado.getEstadoOperativo());
        assertEquals(4, blindado.getNivelBlindaje());
    }

    @Test
    void crearApoyo_almacenaAtributosGeneralesYParticulares() {
        assertNotNull(apoyo);
        assertEquals("A-01", apoyo.getId());
        assertEquals("Ambulancia", apoyo.getModelo());
        assertEquals(2022, apoyo.getAnioFabricacion());
        assertEquals(5000, apoyo.getKilometraje(), 0.001);
        assertEquals(EstadoOperativo.DISPONIBLE, apoyo.getEstadoOperativo());
        assertEquals(TipoFuncion.MEDICO, apoyo.getTipoFuncion());
    }

    @Test
    void vehiculosRecienCreados_sonValidosYNoTienenMisiones() {
        assertTrue(transporte.esValido());
        assertTrue(blindado.esValido());
        assertTrue(apoyo.esValido());
        assertEquals(0, transporte.contarMisionesCompletadas());
        assertTrue(apoyo.getListaDetallesMision().isEmpty());
    }


    @Test
    void vehiculoConDatosEspecificosInvalidos_noEsValido() {
        TransporteTropas sinCapacidad = new TransporteTropas("T-02", "Camion", 2020,
                0, EstadoOperativo.DISPONIBLE, 0);
        Blindado sinBlindaje = new Blindado("B-02", "Blindado", 2020,
                0, EstadoOperativo.DISPONIBLE, 0);
        Apoyo sinFuncion = new Apoyo("A-02", "Apoyo", 2020,
                0, EstadoOperativo.DISPONIBLE, null);
        assertFalse(sinCapacidad.esValido());
        assertFalse(sinBlindaje.esValido());
        assertFalse(sinFuncion.esValido());
    }

    @Test
    void vehiculoConDatosGeneralesInvalidos_noEsValido() {
        Blindado sinModelo = new Blindado("B-03", " ", 2020, 0, EstadoOperativo.DISPONIBLE, 3);
        Blindado anioFuturo = new Blindado("B-04", "Blindado", 3000, 0, EstadoOperativo.DISPONIBLE, 3);
        Blindado kmNegativo = new Blindado("B-05", "Blindado", 2020, -1, EstadoOperativo.DISPONIBLE, 3);
        Blindado sinEstado = new Blindado("B-06", "Blindado", 2020, 0, null, 3);
        assertFalse(sinModelo.esValido());
        assertFalse(anioFuturo.esValido());
        assertFalse(kmNegativo.esValido());
        assertFalse(sinEstado.esValido());
    }

    @Test
    void idDelVehiculo_seLimpiaYNuncaEsNulo() {
        Blindado conEspacios = new Blindado("  B-09  ", "Blindado", 2020, 0, EstadoOperativo.DISPONIBLE, 3);
        Blindado idNulo = new Blindado(null, "Blindado", 2020, 0, EstadoOperativo.DISPONIBLE, 3);
        assertEquals("B-09", conEspacios.getId());
        assertEquals("", idNulo.getId());
        assertFalse(idNulo.esValido());
    }


    @Test
    void settersEspecificos_aceptanValoresValidosYRechazanInvalidos() {
        assertTrue(transporte.setCapacidadSoldados(40));
        assertEquals(40, transporte.getCapacidadSoldados());
        assertFalse(transporte.setCapacidadSoldados(-5));
        assertEquals(40, transporte.getCapacidadSoldados());

        assertTrue(blindado.setNivelBlindaje(6));
        assertEquals(6, blindado.getNivelBlindaje());
        assertFalse(blindado.setNivelBlindaje(0));
        assertEquals(6, blindado.getNivelBlindaje());

        assertTrue(apoyo.setTipoFuncion(TipoFuncion.LOGISTICA));
        assertEquals(TipoFuncion.LOGISTICA, apoyo.getTipoFuncion());
        assertFalse(apoyo.setTipoFuncion(null));
        assertEquals(TipoFuncion.LOGISTICA, apoyo.getTipoFuncion());
    }

    @Test
    void setKilometraje_noPermiteDisminuirElOdometro() {
        assertFalse(transporte.setKilometraje(100));
        assertEquals(12000, transporte.getKilometraje(), 0.001);
        assertTrue(transporte.setKilometraje(13000));
        assertEquals(13000, transporte.getKilometraje(), 0.001);
    }

    @Test
    void setModeloYAnio_validanSusDatos() {
        assertTrue(blindado.setModelo("  Nuevo modelo  "));
        assertEquals("Nuevo modelo", blindado.getModelo());
        assertFalse(blindado.setModelo(""));
        assertFalse(blindado.setModelo(null));
        assertTrue(blindado.setAnioFabricacion(2018));
        assertFalse(blindado.setAnioFabricacion(0));
        assertFalse(blindado.setAnioFabricacion(3000));
        assertEquals(2018, blindado.getAnioFabricacion());
    }

    @Test
    void setEstadoOperativo_rechazaNuloYAceptaMantenimiento() {
        assertFalse(apoyo.setEstadoOperativo(null));
        assertEquals(EstadoOperativo.DISPONIBLE, apoyo.getEstadoOperativo());
        assertTrue(apoyo.setEstadoOperativo(EstadoOperativo.EN_MANTENIMIENTO));
        assertEquals(EstadoOperativo.EN_MANTENIMIENTO, apoyo.getEstadoOperativo());
    }

    @Test
    void toString_incluyeAtributosEspecificosDeCadaTipo() {
        assertTrue(transporte.toString().contains("capacidadSoldados=25"));
        assertTrue(blindado.toString().contains("nivelBlindaje=4"));
        assertTrue(apoyo.toString().contains("tipoFuncion=MEDICO"));
    }
}
