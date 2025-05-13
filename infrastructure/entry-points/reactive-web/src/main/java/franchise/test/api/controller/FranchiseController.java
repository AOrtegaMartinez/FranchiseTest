package franchise.test.api.controller;

import franchise.test.api.dto.FranchiseDTO;
import franchise.test.api.dto.TopStockProductDTO;
import franchise.test.model.Franchise;
import franchise.test.usecase.FranchiseUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/franchises")
@RequiredArgsConstructor
@Tag(name = "Franquicias", description = "API para la gestión de franquicias")
public class FranchiseController {

    private final FranchiseUseCase franchiseUseCase;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear una nueva franquicia", responses = {
            @ApiResponse(responseCode = "201", description = "Franquicia creada exitosamente",
                    content = @Content(schema = @Schema(implementation = FranchiseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public Mono<FranchiseDTO> createFranchise(@RequestBody FranchiseDTO franchiseDTO) {
        Franchise franchise = Franchise.builder()
                .name(franchiseDTO.getName())
                .build();

        return franchiseUseCase.createFranchise(franchise)
                .map(this::mapToDTO);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Obtener todas las franquicias", responses = {
            @ApiResponse(responseCode = "200", description = "Listado de franquicias")
    })
    public Flux<FranchiseDTO> getAllFranchises() {
        return franchiseUseCase.getAllFranchises()
                .map(this::mapToDTO);
    }

    @GetMapping(value = "/{franchiseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Obtener una franquicia por ID", responses = {
            @ApiResponse(responseCode = "200", description = "Franquicia encontrada"),
            @ApiResponse(responseCode = "404", description = "Franquicia no encontrada")
    })
    public Mono<FranchiseDTO> getFranchiseById(@PathVariable(name = "franchiseId") String franchiseId) {
        return franchiseUseCase.getFranchiseById(franchiseId)
                .map(this::mapToDTO)
                .switchIfEmpty(Mono.error(new RuntimeException("Franquicia no encontrada")));
    }

    @GetMapping(value = "/{franchiseId}/top-stock", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Obtener productos con mayor stock por sucursal de una franquicia", responses = {
            @ApiResponse(responseCode = "200", description = "Listado de productos con mayor stock por sucursal"),
            @ApiResponse(responseCode = "404", description = "Franquicia no encontrada")
    })
    public Flux<TopStockProductDTO> getTopStockByFranchiseId(@PathVariable(name = "franchiseId") String franchiseId) {
        return franchiseUseCase.getFranchiseById(franchiseId)
                .switchIfEmpty(Mono.error(new RuntimeException("Franquicia no encontrada")))
                .thenMany(franchiseUseCase.getTopStockByFranchiseId(franchiseId)
                        .map(topStock -> TopStockProductDTO.builder()
                                .branchId(topStock.getBranchId())
                                .branchName(topStock.getBranchName())
                                .products(topStock.getProducts().stream()
                                        .map(product -> ProductMapper.mapToDTO(product))
                                        .toList())
                                .build()));
    }

    private FranchiseDTO mapToDTO(Franchise franchise) {
        return FranchiseDTO.builder()
                .id(franchise.getId())
                .name(franchise.getName())
                .build();
    }
}
