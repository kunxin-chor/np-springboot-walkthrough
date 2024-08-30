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

    // @Query("SELECT p FROM Product p " +
    // "LEFT JOIN FETCH p.category " +
    // "LEFT JOIN FETCH p.tags " +
    // "WHERE " +
    // "(:name IS NULL OR p.name LIKE %:name%) AND " +
    // "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
    // "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
    // "(:category IS NULL OR p.category.name = :category) AND " +
    // "(:tags IS NULL OR :tagCount = (SELECT COUNT(t) FROM p.tags t WHERE t IN
    // :tags))")
    // List<Product> searchProducts(@Param("name") String name,
    // @Param("minPrice") BigDecimal minPrice,
    // @Param("maxPrice") BigDecimal maxPrice,
    // @Param("category") String category,
    // @Param("tags") List<String> tags,
    // @Param("tagCount") long tagCount);

    @Query("SELECT p FROM Product p " +
            "LEFT JOIN FETCH p.category " +
            "LEFT JOIN FETCH p.tags " +
            "WHERE " +
            "(:name IS NULL OR :name = '' OR p.name LIKE %:name%) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
            "(:category = 0 OR p.category.id = :category) AND " +
            "(:tags IS NULL OR :tagCount = 0 OR :tagCount = (SELECT COUNT(t) FROM p.tags t WHERE t IN :tags))")
    List<Product> searchProducts(@Param("name") String name, @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice, @Param("category") Long category, @Param("tags") List<Long> tags,
            @Param("tagCount") long tagCount);

    //  @Query("SELECT p FROM Product p " +
    //         "LEFT JOIN FETCH p.category " +
    //         "LEFT JOIN FETCH p.tags " +
    //         "WHERE " +
    //         "(:name IS NULL OR :name = '' OR p.name LIKE %:name%) AND " +
    //         "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
    //         "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
    //         "(:category = 0 OR p.category.id = :category)") 
        
    // List<Product> searchProducts(@Param("name") String name, @Param("minPrice") BigDecimal minPrice,
    //         @Param("maxPrice") BigDecimal maxPrice, @Param("category") Long category);

}