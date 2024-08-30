package com.example.demo.repo;

import com.example.demo.models.Product;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepo extends JpaRepository<Product, Long> {

    // @Query("SELECT p FROM Product p LEFT JOIN FETCH p.category")
    // List<Product> findAllWithCategories();

    @Query("SELECT DISTINCT p FROM Product p " +
            "LEFT JOIN FETCH p.category " +
            "LEFT JOIN FETCH p.tags")
    List<Product> findAllWithCategoriesAndTags();

      @Query("SELECT p FROM Product p WHERE " +
            "(:name IS NULL OR p.name LIKE %:name%) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
            "(:category IS NULL OR p.category = :category) AND " + 
            "(SELECT COUNT(t) FROM p.tags t WHERE t IN :tags) = :tagCount" +
             "LEFT JOIN FETCH p.category " +
            "LEFT JOIN FETCH p.tags")
    List<Product> searchProducts(@Param("name") String name,
                                 @Param("minPrice") BigDecimal minPrice,
                                 @Param("maxPrice") BigDecimal maxPrice,
                                 @Param("category") String category,
                                 @Param("tags") List<String> tags,
                                 @Param("tagCount") long tagCount);

}