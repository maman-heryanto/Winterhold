package com.winterhold.dto.author;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorRowDto  {
    Long id;
    String fullName;
    Integer age;
    String status;
    String education;

    public AuthorRowDto(Long id, String fullName, LocalDate birthDate, LocalDate deceasedDate, String education) {
        this.id = id;
        this.fullName = fullName;
        this.age = getAge(birthDate, deceasedDate);
        this.status = deceasedDate == null? "Alive" : "Deceased";
        this.education = education;
    }

    private Integer getAge(LocalDate birthDate, LocalDate deceasedDate){
        if(deceasedDate != null){
            return Period.between(birthDate, deceasedDate).getYears();
        }else {
            return Period.between(birthDate, LocalDate.now()).getYears();
        }
    }
}