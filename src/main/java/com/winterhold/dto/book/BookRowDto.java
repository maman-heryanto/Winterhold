package com.winterhold.dto.book;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookRowDto {
    String code;
    String title;
    String author;
    String status;
    LocalDate releaseDate;
    Integer totalPage;

    public BookRowDto(String code, String title, String author, Boolean isBorrowed, LocalDate releaseDate, Integer totalPage) {
        this.code = code;
        this.title = title;
        this.author = author;
        this.status = isBorrowed?"Borrowed":"Available";
        this.releaseDate = releaseDate;
        this.totalPage = totalPage;
    }
}