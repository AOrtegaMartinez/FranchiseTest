package franchise.test.model.gateway;

import franchise.test.model.Franchise;
import franchise.test.model.TopStockProduct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranchiseRepository {
    Mono<Franchise> save(Franchise franchise);

    Mono<Franchise> findById(String id);

    Flux<Franchise> findAll();

    Mono<Void> deleteById(String id);

    Flux<TopStockProduct> findTopStockByFranchiseId(String franchiseId);
}