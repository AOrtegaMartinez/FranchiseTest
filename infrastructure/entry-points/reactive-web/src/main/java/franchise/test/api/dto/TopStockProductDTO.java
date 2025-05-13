package franchise.test.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopStockProductDTO {
    private String branchId;
    private String branchName;
    private List<ProductDTO> products;
}