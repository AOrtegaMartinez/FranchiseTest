package franchise.test.mongo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "branches")
public class BranchEntity {
    @Id
    private String id;
    private String name;
    private String franchiseId;

    @Builder.Default
    private List<ProductEntity> products = new ArrayList<>();
}
