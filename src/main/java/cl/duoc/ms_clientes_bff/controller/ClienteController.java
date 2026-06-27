package cl.duoc.ms_clientes_bff.controller;

import cl.duoc.ms_clientes_bff.model.dto.ClienteDTO;
import cl.duoc.ms_clientes_bff.service.ClienteService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
@Slf4j
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listar() {
        log.info("BFF - GET /api/v1/clientes");
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> crear(@Valid @RequestBody ClienteDTO clienteDTO) {
        log.info("BFF - POST /api/v1/clientes, datos recibidos: {}", clienteDTO);
        clienteDTO.setId(null);         // Forzar el ID a null para que la BD lo genere
        log.debug("ID forzado a null antes de enviar al BS");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clienteService.crearCliente(clienteDTO));
    }

    //Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> obtenerPorId(@PathVariable Long id) {
        log.info("BFF - GET /api/v1/clientes/{}", id);
        return ResponseEntity.ok(clienteService.obtenerClientePorId(id));
    }

    //Actualizar (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ClienteDTO clienteDTO) {
        log.info("BFF - PUT /api/v1/clientes/{}, datos: {}", id, clienteDTO);
        // el ID en el DTO para que coincida con el path
        clienteDTO.setId(id);
        return ResponseEntity.ok(clienteService.actualizarCliente(id, clienteDTO));
    }

    //Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("BFF - DELETE /api/v1/clientes/{}", id);
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }
}