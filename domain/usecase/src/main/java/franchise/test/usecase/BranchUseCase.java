package franchise.test.usecase;

import franchise.test.model.Branch;
import franchise.test.model.Product;
import franchise.test.model.gateway.BranchRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class BranchUseCase {
    private final BranchRepository branchRepository;

    public Mono<Branch> createBranch(Branch branch) {
        return branchRepository.save(branch);
    }

    public Mono<Branch> getBranchById(String id) {
        return branchRepository.findById(id);
    }

    public Flux<Branch> getBranchesByFranchiseId(String franchiseId) {
        return branchRepository.findByFranchiseId(franchiseId);
    }

    public Mono<Branch> addProductToBranch(String branchId, Product product) {
        return branchRepository.addProductToBranch(branchId, product);
    }

    public Mono<Branch> removeProductFromBranch(String branchId, String productId) {
        return branchRepository.removeProductFromBranch(branchId, productId);
    }

    public Mono<Branch> updateProductStock(String branchId, String productId, Integer stock) {
        return branchRepository.updateProductStock(branchId, productId, stock);
    }
}
