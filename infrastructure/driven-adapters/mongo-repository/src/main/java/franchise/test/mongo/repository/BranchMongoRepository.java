package franchise.test.mongo.repository;

import franchise.test.mongo.entity.BranchEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface BranchMongoRepository extends ReactiveMongoRepository<BranchEntity, String> {
    Flux<BranchEntity> findByFranchiseId(String franchiseId);
}
