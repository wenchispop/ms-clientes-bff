package cl.duoc.ms_clientes_bff.client;

import cl.duoc.ms_clientes_bff.model.dto.ClienteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "ms-clientes-bs", url = "${ms-clientes-bs.url}")
public interface ClienteBsFeignClient {

    @GetMapping("/api/v1/clientes")
    List<ClienteDTO> listarClientes();

    @PostMapping("/api/v1/clientes")
    ClienteDTO crearCliente(@RequestBody ClienteDTO clienteDTO);

    //Obtener por ID
    @GetMapping("/api/v1/clientes/{id}")
    ClienteDTO obtenerClientePorId(@PathVariable("id") Long id);

    //Actualizar
    @PutMapping("/api/v1/clientes/{id}")
    ClienteDTO actualizarCliente(@PathVariable("id") Long id, @RequestBody ClienteDTO clienteDTO);

    //Eliminar
    @DeleteMapping("/api/v1/clientes/{id}")
    void eliminarCliente(@PathVariable("id") Long id);
}