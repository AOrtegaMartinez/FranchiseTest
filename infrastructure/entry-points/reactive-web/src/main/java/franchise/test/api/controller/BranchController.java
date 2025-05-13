package franchise.test.api.controller;

import franchise.test.api.dto.BranchDTO;
import franchise.test.api.dto.ProductDTO;
import franchise.test.api.dto.StockUpdateDTO;
import franchise.test.model.Branch;
import franchise.test.model.Product;
import franchise.test.usecase.BranchUseCase;
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
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Tag(name = "Sucursales", description = "API para la gestión de sucursales y productos")
public class BranchController {

    private final BranchUseCase branchUseCase;
    private final FranchiseUseCase franchiseUseCase;

    @PostMapping(value = "/franchises/{franchiseId}/branches", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Agregar una sucursal a una franquicia", responses = {
            @ApiResponse(responseCode = "201", description = "Sucursal agregada exitosamente",
                    content = @Content(schema = @Schema(implementation = BranchDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Franquicia no encontrada")
    })
    public Mono<BranchDTO> addBranchToFranchise(
            @PathVariable(name = "franchiseId") String franchiseId,
            @RequestBody BranchDTO branchDTO) {

        return franchiseUseCase.getFranchiseById(franchiseId)
                .switchIfEmpty(Mono.error(new RuntimeException("Franquicia no encontrada")))
                .flatMap(franchise -> {
                    Branch branch = Branch.builder()
                            .name(branchDTO.getName())
                            .franchiseId(franchiseId)
                            .build();

                    return branchUseCase.createBranch(branch);
                })
                .map(this::mapToDTO);
    }

    @PostMapping(value = "/branches/{branchId}/products", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Agregar un producto a una sucursal", responses = {
            @ApiResponse(responseCode = "201", description = "Producto agregado exitosamente",
                    content = @Content(schema = @Schema(implementation = BranchDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    public Mono<BranchDTO> addProductToBranch(
            @PathVariable(name = "branchId") String branchId,
            @RequestBody ProductDTO productDTO) {

        Product product = Product.builder()
                .name(productDTO.getName())
                .stock(productDTO.getStock())
                .build();

        return branchUseCase.addProductToBranch(branchId, product)
                .map(this::mapToDTO);
    }

    @DeleteMapping(value = "/branches/{branchId}/products/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Eliminar un producto de una sucursal", responses = {
            @ApiResponse(responseCode = "200", description = "Producto eliminado exitosamente",
                    content = @Content(schema = @Schema(implementation = BranchDTO.class))),
            @ApiResponse(responseCode = "404", description = "Sucursal o producto no encontrado")
    })
    public Mono<BranchDTO> removeProductFromBranch(
            @PathVariable(name = "branchId") String branchId,
            @PathVariable(name = "productId") String productId) {

        return branchUseCase.removeProductFromBranch(branchId, productId)
                .map(this::mapToDTO);
    }

    @PutMapping(value = "/branches/{branchId}/products/{productId}/stock", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Actualizar el stock de un producto", responses = {
            @ApiResponse(responseCode = "200", description = "Stock actualizado exitosamente",
                    content = @Content(schema = @Schema(implementation = BranchDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Sucursal o producto no encontrado")
    })
    public Mono<BranchDTO> updateProductStock(
            @PathVariable("branchId") String branchId,
            @PathVariable("productId") String productId,
            @RequestBody StockUpdateDTO stockUpdateDTO) {

        return branchUseCase.updateProductStock(branchId, productId, stockUpdateDTO.getStock())
                .map(this::mapToDTO);
    }

    private BranchDTO mapToDTO(Branch branch) {
        return BranchDTO.builder()
                .id(branch.getId())
                .name(branch.getName())
                .franchiseId(branch.getFranchiseId())
                .products(branch.getProducts() != null ?
                        branch.getProducts().stream()
                                .map(ProductMapper::mapToDTO)
                                .toList() :
                        null)
                .build();
    }
}
