package franchise.test.config;

import franchise.test.model.gateway.BranchRepository;
import franchise.test.model.gateway.FranchiseRepository;
import franchise.test.usecase.BranchUseCase;
import franchise.test.usecase.FranchiseUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseBeanConfig {

    @Bean
    public FranchiseUseCase franchiseUseCase(FranchiseRepository franchiseRepository) {
        return new FranchiseUseCase(franchiseRepository);
    }

    @Bean
    public BranchUseCase branchUseCase(BranchRepository branchRepository) {
        return new BranchUseCase(branchRepository);
    }

}
