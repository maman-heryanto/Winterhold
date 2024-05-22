package com.winterhold.dao;

import com.winterhold.dto.DropdownDTO;
import com.winterhold.dto.author.AuthorHeaderDto;
import com.winterhold.dto.author.AuthorRowDto;
import com.winterhold.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query("""
    SELECT new com.winterhold.dto.author.AuthorRowDto(
        aut.id,
        CONCAT(aut.title,'. ',aut.firstName,' ',aut.lastName),
        aut.birthDate,
        aut.deceasedDate,
        aut.education
    )
    FROM Author As aut
    WHERE CONCAT(aut.title,'. ',aut.firstName,' ',aut.lastName) LIKE %:name%
    """)
    public Page<AuthorRowDto> getAuthorTable(@Param("name") String name, Pageable pageable);

    @Query("""
         SELECT new com.winterhold.dto.DropdownDTO(
             aut.id, 
             CONCAT(aut.title,'. ',aut.firstName,' ',aut.lastName)
         )
         FROM Author AS aut
     """)
    public List<DropdownDTO> getDropdownList();

    @Query("""
    SELECT new com.winterhold.dto.author.AuthorHeaderDto(
        CONCAT(aut.title,'. ', aut.firstName,' ',aut.lastName),
        aut.birthDate,
        aut.deceasedDate,
        aut.education,
        aut.summary 
    )
    FROM Author AS aut
    WHERE aut.id = :authorId
    """)
    public AuthorHeaderDto getAuthorHeader(@Param("authorId") Long authorId);
}
