package ewhabackendDDDstudy.order.command.domain;

import jakarta.persistence.*;

public class Product {
    private CategoryId categoryId;

    @Entity
    @Table(name = "product")
    public class Product {
        @EmbeddedId
        private ProductId id;

        @ElementCollection
        @CollectionTable(name = "product_category",
                joinColumns = @JoinColumn(name = "product_id"))
        private Set<CategoryId> categoryIds;
    }
}
