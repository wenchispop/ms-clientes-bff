package cl.duoc.ms_clientes_bff.service;

import cl.duoc.ms_clientes_bff.client.ClienteBsFeignClient;
import cl.duoc.ms_clientes_bff.model.dto.ClienteDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ClienteService {

    private final ClienteBsFeignClient clienteBsFeignClient;

    public ClienteService(ClienteBsFeignClient clienteBsFeignClient) {
        this.clienteBsFeignClient = clienteBsFeignClient;
    }

    public List<ClienteDTO> listarClientes() {
        log.info("BFF - solicitando lista de clientes al BS");
        return clienteBsFeignClient.listarClientes();
    }

    public ClienteDTO crearCliente(ClienteDTO clienteDTO) {
        log.info("BFF - enviando cliente al BS para creación: {}", clienteDTO);
        return clienteBsFeignClient.crearCliente(clienteDTO);
    }

    //Obtener por ID
    public ClienteDTO obtenerClientePorId(Long id) {
        log.info("BFF - solicitando cliente {} al BS", id);
        return clienteBsFeignClient.obtenerClientePorId(id);
    }

    //Actualizar
    public ClienteDTO actualizarCliente(Long id, ClienteDTO clienteDTO) {
        log.info("BFF - enviando actualización del cliente {} al BS", id);
        return clienteBsFeignClient.actualizarCliente(id, clienteDTO);
    }

    //Eliminar
    public void eliminarCliente(Long id) {
        log.info("BFF - eliminando cliente {} en BS", id);
        clienteBsFeignClient.eliminarCliente(id);
    }
}