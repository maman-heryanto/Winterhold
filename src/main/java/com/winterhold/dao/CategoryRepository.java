package com.winterhold.dao;

import com.winterhold.dto.category.CategoryRowDto;
import com.winterhold.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, String> {

    @Query(value = """
    SELECT new com.winterhold.dto.category.CategoryRowDto(
        cat.name,
        cat.floor,
        cat.isle,
        cat.bay,
        (SELECT COUNT(bk.code)
         FROM Book AS bk
         WHERE bk.categoryName = cat.name
        )
    )
    FROM Category AS cat
    WHERE cat.name LIKE %:name%
    """)

//@Query(value = """
//    SELECT new com.winterhold.dto.category.CategoryRowDto(
//        cat.name,
//        cat.floor,
//        cat.isle,
//        cat.bay,
//        tot.totalBook
//    )
//    FROM Category AS cat
//        JOIN (
//             SELECT bk.categoryName, COUNT(bk.categoryName) [totalBook]
//             FROM Book As bk
//             GROUP BY bk.categoryName
//             AS tb ON tb.CategoryName = :name
//        ) AS tot
//    """)
//    @Query("""
//    SELECT new com.winterhold.dto.category.CategoryRowDto(
//        cat.name,
//        cat.floor,
//        cat.isle,
//        cat.bay,
//        count(bk.code)
//    )
//    FROM Book AS bk
//        RIGHT JOIN bk.category AS cat
//    GROUP BY cat.name, cat.floor, cat.isle, cat.bay
//    """)
    public Page<CategoryRowDto> getCategoryTable (@Param("name") String name, Pageable pageable);

}
