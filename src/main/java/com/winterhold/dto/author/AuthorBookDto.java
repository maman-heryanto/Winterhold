package com.winterhold.dto.author;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorBookDto {
    private String bookTitle;
    private String category;
    private String status;
    private LocalDate releaseDate;
    private Integer totalPage;

    public AuthorBookDto(String bookTitle, String category, Boolean status, LocalDate releaseDate, Integer totalPage) {
        this.bookTitle = bookTitle;
        this.category = category;
        this.status = status?"Borrowed":"Available";
        this.releaseDate = releaseDate;
        this.totalPage = totalPage;
    }
}
