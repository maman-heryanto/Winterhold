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
public class AuthorHeaderDto {
    private String fullName;
    private LocalDate birthDate;
    private LocalDate deceaseDate;
    private String education;
    private String summary;
}
