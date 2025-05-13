package franchise.test.usecase;

import franchise.test.model.Franchise;
import franchise.test.model.TopStockProduct;
import franchise.test.model.gateway.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FranchiseUseCase {
    private final FranchiseRepository franchiseRepository;

    public Mono<Franchise> createFranchise(Franchise franchise) {
        return franchiseRepository.save(franchise);
    }

    public Mono<Franchise> getFranchiseById(String id) {
        return franchiseRepository.findById(id);
    }

    public Flux<Franchise> getAllFranchises() {
        return franchiseRepository.findAll();
    }

    public Flux<TopStockProduct> getTopStockByFranchiseId(String franchiseId) {
        return franchiseRepository.findTopStockByFranchiseId(franchiseId);
    }
}
