package ewhabackendDDDstudy.service;

import ewhabackendDDDstudy.Repository.CategoryRepository;
import ewhabackendDDDstudy.Repository.ProductRepository;
import ewhabackendDDDstudy.domain.Category;
import ewhabackendDDDstudy.domain.Product;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductListService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;
    public Page<Product> getProductOfCategory(Long categoryId, int page, int size) {
        Category category = categoryRepository.findById(categoryId);
        checkCategory(category);
        List<Product> products = productRepository.findByCategoryId(category.getId(), page, size);
        int totalCount = productRepository.countsByCategoryId(category.getId());
        return new Page(page, size, totalCount, products);
    }
}