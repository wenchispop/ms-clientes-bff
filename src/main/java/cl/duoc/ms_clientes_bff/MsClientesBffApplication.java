package cl.duoc.ms_clientes_bff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsClientesBffApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsClientesBffApplication.class, args);
	}

}
