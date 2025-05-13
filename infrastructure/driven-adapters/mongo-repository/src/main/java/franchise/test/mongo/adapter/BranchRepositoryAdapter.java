package franchise.test.mongo.adapter;

import franchise.test.model.Branch;
import franchise.test.model.Product;
import franchise.test.model.gateway.BranchRepository;
import franchise.test.mongo.entity.BranchEntity;
import franchise.test.mongo.entity.ProductEntity;
import franchise.test.mongo.helper.MapperUtils;
import franchise.test.mongo.repository.BranchMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class BranchRepositoryAdapter implements BranchRepository {
    private final BranchMongoRepository branchRepository;
    private final MapperUtils mapper;

    @Override
    public Mono<Branch> save(Branch branch) {
        return Mono.just(branch)
                .map(b -> mapper.mapToEntity(b, BranchEntity.class))
                .flatMap(branchRepository::save)
                .map(entity -> mapper.mapToEntity(entity, Branch.class));
    }

    @Override
    public Mono<Branch> findById(String id) {
        return branchRepository.findById(id)
                .map(entity -> mapper.mapToEntity(entity, Branch.class));
    }

    @Override
    public Flux<Branch> findByFranchiseId(String franchiseId) {
        return branchRepository.findByFranchiseId(franchiseId)
                .map(entity -> mapper.mapToEntity(entity, Branch.class));
    }

    @Override
    public Mono<Branch> addProductToBranch(String branchId, Product product) {
        return branchRepository.findById(branchId)
                .switchIfEmpty(Mono.error(new RuntimeException("Sucursal no encontrada")))
                .flatMap(branch -> {
                    ProductEntity productEntity = mapper.mapToEntity(product, ProductEntity.class);
                    if (productEntity.getId() == null) {
                        productEntity.setId(UUID.randomUUID().toString());
                    }

                    if (branch.getProducts() == null) {
                        branch.setProducts(new ArrayList<>());
                    }
                    branch.getProducts().add(productEntity);

                    return branchRepository.save(branch);
                })
                .map(entity -> mapper.mapToEntity(entity, Branch.class));
    }

    @Override
    public Mono<Branch> removeProductFromBranch(String branchId, String productId) {
        return branchRepository.findById(branchId)
                .switchIfEmpty(Mono.error(new RuntimeException("Sucursal no encontrada")))
                .flatMap(branch -> {
                    if (branch.getProducts() != null) {
                        branch.setProducts(branch.getProducts().stream()
                                .filter(p -> !p.getId().equals(productId))
                                .toList());
                    }
                    return branchRepository.save(branch);
                })
                .map(entity -> mapper.mapToEntity(entity, Branch.class));
    }

    @Override
    public Mono<Branch> updateProductStock(String branchId, String productId, Integer stock) {
        return branchRepository.findById(branchId)
                .switchIfEmpty(Mono.error(new RuntimeException("Sucursal no encontrada")))
                .flatMap(branch -> {
                    boolean productFound = false;

                    if (branch.getProducts() != null) {
                        for (ProductEntity product : branch.getProducts()) {
                            if (product.getId().equals(productId)) {
                                product.setStock(stock);
                                productFound = true;
                                break;
                            }
                        }
                    }

                    if (!productFound) {
                        return Mono.error(new RuntimeException("Producto no encontrado en la sucursal"));
                    }

                    return branchRepository.save(branch);
                })
                .map(entity -> mapper.mapToEntity(entity, Branch.class));
    }
}
