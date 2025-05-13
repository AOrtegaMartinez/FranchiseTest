package franchise.test;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "API de Gestión de Franquicias",
        version = "1.0",
        description = "API REST para la gestión de franquicias, sucursales y productos"
))
public class FranchiseApplication {
    public static void main(String[] args) {
        SpringApplication.run(FranchiseApplication.class, args);
    }
}
