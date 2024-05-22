package com.winterhold.dto.loan;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpsertLoanDto {
    Long id;

    @NotBlank(message = "Harus pilih customer")
    String customerNumber;

    @NotBlank(message = "harus pilih buku")
    String bookCode;

    @NotNull(message = "harus masukkan tanggal pinjam")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate loanDate;

    LocalDate returnDate;

    String Note;
}