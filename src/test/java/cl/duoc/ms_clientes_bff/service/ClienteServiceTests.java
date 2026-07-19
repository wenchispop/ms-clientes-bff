package cl.duoc.ms_clientes_bff.service;

import cl.duoc.ms_clientes_bff.model.dto.ClienteDTO;
import cl.duoc.ms_clientes_bff.client.ClienteBsFeignClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
class ClienteServiceTests {

    @Autowired
    private ClienteService clienteService;

    @MockBean
    private ClienteBsFeignClient clienteBsFeignClient;

    private ClienteDTO clienteDTO;

    @BeforeEach
    void setUp() {
        // Preparar datos de prueba
        clienteDTO = new ClienteDTO();
        clienteDTO.setId(1L);
        clienteDTO.setNombre("Juan Pérez");
        clienteDTO.setEmail("juan@email.com");
        clienteDTO.setTelefono("+56912345678");
        clienteDTO.setDireccion("Calle Principal 123");
    }

    // TEST 1: Listar clientes
    @Test
    void testListarClientes_Success() {
        List<ClienteDTO> clientes = Arrays.asList(clienteDTO);
        when(clienteBsFeignClient.listarClientes()).thenReturn(clientes);

        List<ClienteDTO> resultado = clienteService.listarClientes();

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Juan Pérez", resultado.get(0).getNombre());
    }

    // TEST 2: Obtener cliente por ID
    @Test
    void testObtenerClientePorId_Success() {
        when(clienteBsFeignClient.obtenerClientePorId(1L)).thenReturn(clienteDTO);

        ClienteDTO resultado = clienteService.obtenerClientePorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Juan Pérez", resultado.getNombre());
        assertEquals("juan@email.com", resultado.getEmail());
    }

    // TEST 3: Crear cliente
    @Test
    void testCrearCliente_Success() {
        ClienteDTO nuevoCliente = new ClienteDTO();
        nuevoCliente.setNombre("Carlos López");
        nuevoCliente.setEmail("carlos@email.com");

        ClienteDTO clienteGuardado = new ClienteDTO();
        clienteGuardado.setId(2L);
        clienteGuardado.setNombre("Carlos López");
        clienteGuardado.setEmail("carlos@email.com");

        when(clienteBsFeignClient.crearCliente(any(ClienteDTO.class))).thenReturn(clienteGuardado);

        ClienteDTO resultado = clienteService.crearCliente(nuevoCliente);

        assertNotNull(resultado);
        assertNotNull(resultado.getId());
        assertEquals("Carlos López", resultado.getNombre());
    }

    // TEST 4: Actualizar cliente
    @Test
    void testActualizarCliente_Success() {
        ClienteDTO clienteActualizado = new ClienteDTO();
        clienteActualizado.setId(1L);
        clienteActualizado.setNombre("Juan Pérez Updated");
        clienteActualizado.setEmail("juannuevo@email.com");

        when(clienteBsFeignClient.actualizarCliente(1L, clienteActualizado))
                .thenReturn(clienteActualizado);

        ClienteDTO resultado = clienteService.actualizarCliente(1L, clienteActualizado);

        assertNotNull(resultado);
        assertEquals("Juan Pérez Updated", resultado.getNombre());
        assertEquals("juannuevo@email.com", resultado.getEmail());
    }

    // TEST 5: Eliminar cliente
    @Test
    void testEliminarCliente_Success() {
        doNothing().when(clienteBsFeignClient).eliminarCliente(1L);

        assertDoesNotThrow(() -> clienteService.eliminarCliente(1L));
    }

    // TEST 6: Listar clientes vacio
    @Test
    void testListarClientes_Empty() {
        when(clienteBsFeignClient.listarClientes()).thenReturn(Arrays.asList());

        List<ClienteDTO> resultado = clienteService.listarClientes();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    // TEST 7: Validar nombre no es nulo en cliente
    @Test
    void testCrearCliente_NombreNoNulo() {
        ClienteDTO nuevoCliente = new ClienteDTO();
        nuevoCliente.setNombre("Test");
        nuevoCliente.setEmail("test@email.com");

        assertNotNull(nuevoCliente.getNombre());
        assertEquals("Test", nuevoCliente.getNombre());
    }

    // TEST 8: Validar email no es nulo en cliente
    @Test
    void testCrearCliente_EmailNoNulo() {
        ClienteDTO nuevoCliente = new ClienteDTO();
        nuevoCliente.setNombre("Test");
        nuevoCliente.setEmail("test@email.com");

        assertNotNull(nuevoCliente.getEmail());
        assertEquals("test@email.com", nuevoCliente.getEmail());
    }
}