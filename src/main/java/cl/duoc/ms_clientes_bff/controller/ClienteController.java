package cl.duoc.ms_clientes_bff.controller;

import cl.duoc.ms_clientes_bff.model.dto.ClienteDTO;
import cl.duoc.ms_clientes_bff.service.ClienteService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
@Slf4j
@Tag(name = "Clientes", description = "API para gestionar clientes del Hotel de Gatos")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    @Operation(summary = "Listar todos los clientes", description = "Obtiene la lista completa de clientes registrados en el sistema")
    @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteDTO.class)))
    public ResponseEntity<List<ClienteDTO>> listar() {
        log.info("BFF - GET /api/v1/clientes");
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo cliente", description = "Crea un nuevo cliente en el sistema. El ID se genera automáticamente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o incompletos")
    })
    public ResponseEntity<ClienteDTO> crear(@Valid @org.springframework.web.bind.annotation.RequestBody ClienteDTO clienteDTO) {
        log.info("BFF - POST /api/v1/clientes, datos recibidos: {}", clienteDTO);
        clienteDTO.setId(null);         // Forzar el ID a null para que la BD lo genere
        log.debug("ID forzado a null antes de enviar al BS");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clienteService.crearCliente(clienteDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener cliente por ID", description = "Obtiene los detalles de un cliente específico usando su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteDTO.class))),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ResponseEntity<ClienteDTO> obtenerPorId(@PathVariable Long id) {
        log.info("BFF - GET /api/v1/clientes/{}", id);
        return ResponseEntity.ok(clienteService.obtenerClientePorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cliente", description = "Actualiza los datos de un cliente existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteDTO.class))),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<ClienteDTO> actualizar(
            @PathVariable Long id,
            @Valid @org.springframework.web.bind.annotation.RequestBody ClienteDTO clienteDTO) {
        log.info("BFF - PUT /api/v1/clientes/{}, datos: {}", id, clienteDTO);
        clienteDTO.setId(id);
        return ResponseEntity.ok(clienteService.actualizarCliente(id, clienteDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente del sistema de forma permanente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cliente eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("BFF - DELETE /api/v1/clientes/{}", id);
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }
}