package com.winterhold.dao;

import com.winterhold.dto.DropdownDTO;
import com.winterhold.dto.author.AuthorBookDto;
import com.winterhold.dto.author.AuthorRowDto;
import com.winterhold.dto.book.BookRowDto;
import com.winterhold.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String>{
    @Query("""
        SELECT COUNT(bk.code)
        FROM Book AS bk
        WHERE bk.authorId = :authorId
    """)
    public Long countByAuthorId(@Param("authorId") Long authorId);

    @Query("""
        SELECT COUNT(bk.code)
        FROM Book AS bk
        WHERE bk.categoryName = :categoryName
    """)
    public Long countByCategoryName(@Param("categoryName") String categoryName);

    @Query("""
        SELECT new com.winterhold.dto.book.BookRowDto(
            bk.code,
            bk.title,
            CONCAT(auth.title,'. ', auth.firstName,' ', auth.lastName),
            bk.isBorrowed,
            bk.releaseDate,
            bk.totalPage
        )
        FROM Book AS bk
            JOIN bk.author AS auth
        WHERE bk.categoryName LIKE :categoryName AND
        bk.title LIKE %:title% AND
        CONCAT(auth.title,'. ', auth.firstName,' ', auth.lastName) LIKE %:author%
    """)
    public Page<BookRowDto> getBookByCategory(@Param("categoryName") String categoryName,
                                              @Param("title") String title,
                                              @Param("author") String author,
                                              Pageable pageable);

    @Query("""
         SELECT new com.winterhold.dto.DropdownDTO(
             bk.code, 
             bk.title
         )
         FROM Book AS bk
         WHERE bk.isBorrowed = false 
     """)
    public List<DropdownDTO> getDropdownList();

    @Query("""
        SELECT new com.winterhold.dto.author.AuthorBookDto(
            bk.title,
            bk.categoryName,
            bk.isBorrowed,
            bk.releaseDate,
            bk.totalPage
        )
        FROM Book AS bk
        WHERE bk.authorId = :authorId
    """)
    public Page<AuthorBookDto> getBookByAuthor(@Param("authorId") Long authorId, Pageable pageable);
}
