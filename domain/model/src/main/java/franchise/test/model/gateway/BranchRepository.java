package franchise.test.model.gateway;

import franchise.test.model.Branch;
import franchise.test.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BranchRepository {
    Mono<Branch> save(Branch branch);

    Mono<Branch> findById(String id);

    Flux<Branch> findByFranchiseId(String franchiseId);

    Mono<Branch> addProductToBranch(String branchId, Product product);

    Mono<Branch> removeProductFromBranch(String branchId, String productId);

    Mono<Branch> updateProductStock(String branchId, String productId, Integer stock);
}
