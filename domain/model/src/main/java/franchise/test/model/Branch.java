package franchise.test.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Branch {
    private String id;
    private String name;
    private String franchiseId;

    @Builder.Default
    private List<Product> products = new ArrayList<>();
}