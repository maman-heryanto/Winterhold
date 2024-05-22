package com.winterhold.service;

import com.winterhold.dao.AuthorRepository;
import com.winterhold.dao.BookRepository;
import com.winterhold.dto.author.AuthorBookDto;
import com.winterhold.dto.author.AuthorHeaderDto;
import com.winterhold.dto.author.AuthorRowDto;
import com.winterhold.dto.author.UpsertAuthorDto;
import com.winterhold.entity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookRepository bookRepository;

    private final int rowsPerPage = 10;

    public Page<AuthorRowDto> getAuthorTable(String name, Integer page){
        var pageable = PageRequest.of(page-1, rowsPerPage, Sort.by("id"));
        return authorRepository.getAuthorTable(name, pageable);
    }

    public Author saveAuthor(UpsertAuthorDto dto){
        var entity = new Author(
                dto.getId(),
                dto.getTitle(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getBirthDate(),
                dto.getDeceasedDate(),
                dto.getEducation(),
                dto.getSummary()

        );
        var hasilSave = authorRepository.save(entity);

        return hasilSave;
    }

    public UpsertAuthorDto getSingleAuthor(Long id){
        var selectedEntity = authorRepository.findById(id).get();
        var dto = new UpsertAuthorDto(
                selectedEntity.getId(),
                selectedEntity.getTitle(),
                selectedEntity.getFirstName(),
                selectedEntity.getLastName(),
                selectedEntity.getBirthDate(),
                selectedEntity.getDeceasedDate(),
                selectedEntity.getEducation(),
                selectedEntity.getSummary()
        );
        return dto;
    }



    public void deleteAuthor(Long id){
        authorRepository.deleteById(id);
    }

    public Long totalDependentBook(Long id){
        return bookRepository.countByAuthorId(id);
    }

    public Page<AuthorBookDto> getBookByAuthor(Long id, Integer page){
        var pageable = PageRequest.of(page-1, rowsPerPage, Sort.by("id"));
        return bookRepository.getBookByAuthor(id, pageable);
    }

    public AuthorHeaderDto getAuthorHeader(Long id){
        return authorRepository.getAuthorHeader(id);
    }

}
