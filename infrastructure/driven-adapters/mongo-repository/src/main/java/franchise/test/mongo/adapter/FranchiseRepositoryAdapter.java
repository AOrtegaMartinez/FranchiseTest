package franchise.test.mongo.adapter;

import franchise.test.model.Franchise;
import franchise.test.model.Product;
import franchise.test.model.TopStockProduct;
import franchise.test.model.gateway.FranchiseRepository;
import franchise.test.mongo.entity.FranchiseEntity;
import franchise.test.mongo.helper.MapperUtils;
import franchise.test.mongo.repository.BranchMongoRepository;
import franchise.test.mongo.repository.FranchiseMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class FranchiseRepositoryAdapter implements FranchiseRepository {
    private final FranchiseMongoRepository franchiseRepository;
    private final BranchMongoRepository branchRepository;
    private final MapperUtils mapper;

    @Override
    public Mono<Franchise> save(Franchise franchise) {
        return Mono.just(franchise)
                .map(f -> mapper.mapToEntity(f, FranchiseEntity.class))
                .flatMap(franchiseRepository::save)
                .map(entity -> mapper.mapToEntity(entity, Franchise.class));
    }

    @Override
    public Mono<Franchise> findById(String id) {
        return franchiseRepository.findById(id)
                .map(entity -> mapper.mapToEntity(entity, Franchise.class));
    }

    @Override
    public Flux<Franchise> findAll() {
        return franchiseRepository.findAll()
                .map(entity -> mapper.mapToEntity(entity, Franchise.class));
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return franchiseRepository.deleteById(id);
    }

    @Override
    public Flux<TopStockProduct> findTopStockByFranchiseId(String franchiseId) {
        return branchRepository.findByFranchiseId(franchiseId)
                .flatMap(branch -> {
                    if (branch.getProducts() == null || branch.getProducts().isEmpty()) {
                        return Mono.empty();
                    }

                    Integer maxStock = branch.getProducts().stream()
                            .map(p -> p.getStock())
                            .max(Integer::compare)
                            .orElse(0);

                    var topProducts = branch.getProducts().stream()
                            .filter(p -> p.getStock().equals(maxStock))
                            .map(p -> mapper.mapToEntity(p, Product.class))
                            .collect(Collectors.toList());

                    return Mono.just(TopStockProduct.builder()
                            .branchId(branch.getId())
                            .branchName(branch.getName())
                            .products(topProducts)
                            .build());
                });
    }
}