package franchise.test.api.controller;

import franchise.test.api.dto.ProductDTO;
import franchise.test.model.Product;

public class ProductMapper {

    public static ProductDTO mapToDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .stock(product.getStock())
                .build();
    }

    public static Product mapToEntity(ProductDTO productDTO) {
        return Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .stock(productDTO.getStock())
                .build();
    }
}
