package com.winterhold.dto.loan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanDetailDto {
    private String bookTitle;
    private String bookCategory;
    private String author;
    private Integer floor;
    private String isle;
    private String bay;
    private String membershipNumber;
    private String fullName;
    private String phone;

    @DateTimeFormat(pattern = "dd/MM/yyy")
    private LocalDate memberExpiredDate;

}
