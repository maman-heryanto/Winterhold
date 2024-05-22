package com.winterhold.dto.loan;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanRowDto{
    Long id;
    String customerName;
    String bookTitle;
    LocalDate loanDate;
    LocalDate dueDate;
    LocalDate returnDate;
}