package cl.duoc.ms_clientes_bff.controller;

import cl.duoc.ms_clientes_bff.model.dto.ClienteDTO;
import cl.duoc.ms_clientes_bff.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ClienteControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClienteService clienteService;

    private ClienteDTO clienteDTO;

    @BeforeEach
    void setUp() {
        // Preparar datos de prueba comunes
        clienteDTO = new ClienteDTO();
        clienteDTO.setId(1L);
        clienteDTO.setNombre("Juan Pérez");
        clienteDTO.setEmail("juan@email.com");
        clienteDTO.setTelefono("+56912345678");
        clienteDTO.setDireccion("Calle Principal 123");
    }

    // TEST 1: Listar todos los clientes (GET /api/v1/clientes)
    @Test
    void testListarClientes_Success() throws Exception {
        ClienteDTO cliente2 = new ClienteDTO();
        cliente2.setId(2L);
        cliente2.setNombre("María García");
        cliente2.setEmail("maria@email.com");
        cliente2.setTelefono("+56987654321");
        cliente2.setDireccion("Avenida Secundaria 456");

        List<ClienteDTO> clientes = Arrays.asList(clienteDTO, cliente2);
        when(clienteService.listarClientes()).thenReturn(clientes);

        mockMvc.perform(get("/api/v1/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Juan Pérez"))
                .andExpect(jsonPath("$[0].email").value("juan@email.com"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nombre").value("María García"));
    }

    // TEST 2: Obtener cliente por ID (GET /api/v1/clientes/{id})
    @Test
    void testObtenerClientePorId_Success() throws Exception {
        when(clienteService.obtenerClientePorId(1L)).thenReturn(clienteDTO);

        mockMvc.perform(get("/api/v1/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan Pérez"))
                .andExpect(jsonPath("$.email").value("juan@email.com"))
                .andExpect(jsonPath("$.telefono").value("+56912345678"))
                .andExpect(jsonPath("$.direccion").value("Calle Principal 123"));
    }

    // TEST 3: Crear cliente (POST /api/v1/clientes)
    @Test
    void testCrearCliente_Success() throws Exception {
        ClienteDTO nuevoCliente = new ClienteDTO();
        nuevoCliente.setNombre("Carlos López");
        nuevoCliente.setEmail("carlos@email.com");
        nuevoCliente.setTelefono("+56911111111");
        nuevoCliente.setDireccion("Calle Nueva 789");

        ClienteDTO clienteGuardado = new ClienteDTO();
        clienteGuardado.setId(3L);
        clienteGuardado.setNombre("Carlos López");
        clienteGuardado.setEmail("carlos@email.com");
        clienteGuardado.setTelefono("+56911111111");
        clienteGuardado.setDireccion("Calle Nueva 789");

        when(clienteService.crearCliente(any(ClienteDTO.class))).thenReturn(clienteGuardado);

        mockMvc.perform(post("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevoCliente)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.nombre").value("Carlos López"));
    }

    // TEST 4: Actualizar cliente (PUT /api/v1/clientes/{id})
    @Test
    void testActualizarCliente_Success() throws Exception {
        ClienteDTO clienteActualizado = new ClienteDTO();
        clienteActualizado.setId(1L);
        clienteActualizado.setNombre("Juan Pérez Updated");
        clienteActualizado.setEmail("juannuevo@email.com");
        clienteActualizado.setTelefono("+56912345678");
        clienteActualizado.setDireccion("Calle Actualizada 999");

        when(clienteService.actualizarCliente(eq(1L), any(ClienteDTO.class)))
                .thenReturn(clienteActualizado);

        mockMvc.perform(put("/api/v1/clientes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteActualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan Pérez Updated"))
                .andExpect(jsonPath("$.email").value("juannuevo@email.com"));
    }

    // TEST 5: Eliminar cliente (DELETE /api/v1/clientes/{id})
    @Test
    void testEliminarCliente_Success() throws Exception {
        doNothing().when(clienteService).eliminarCliente(1L);

        mockMvc.perform(delete("/api/v1/clientes/1"))
                .andExpect(status().isNoContent());
    }

    // TEST 6: Crear cliente SIN NOMBRE (validación)
    @Test
    void testCrearCliente_SinNombre_BadRequest() throws Exception {
        ClienteDTO clienteSinNombre = new ClienteDTO();
        clienteSinNombre.setEmail("test@email.com");

        mockMvc.perform(post("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteSinNombre)))
                .andExpect(status().isBadRequest());
    }

    // TEST 7: Crear cliente SIN EMAIL (validación)
    @Test
    void testCrearCliente_SinEmail_BadRequest() throws Exception {
        ClienteDTO clienteSinEmail = new ClienteDTO();
        clienteSinEmail.setNombre("Juan");

        mockMvc.perform(post("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteSinEmail)))
                .andExpect(status().isBadRequest());
    }

    // TEST 8: Crear cliente CON EMAIL INVÁLIDO (validación)
    @Test
    void testCrearCliente_EmailInvalido_BadRequest() throws Exception {
        ClienteDTO clienteEmailInvalido = new ClienteDTO();
        clienteEmailInvalido.setNombre("Juan");
        clienteEmailInvalido.setEmail("email-invalido"); // Sin @

        mockMvc.perform(post("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteEmailInvalido)))
                .andExpect(status().isBadRequest());
    }
}