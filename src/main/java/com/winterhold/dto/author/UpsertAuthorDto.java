package com.winterhold.dto.author;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpsertAuthorDto {

    private Long id;

    @Size(message = "Jumlah karakter maksimal 10", max = 10)
    @NotBlank(message = "Title harus diisi")
    String title;

    @Size(message = "Jumlah karakter maksimal 50", max = 50)
    @NotBlank(message = "First name harus diisi")
    String firstName;

    @Size(message = "Jumlah karakter maksimal 50", max = 50)
    String lastName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Birth date harus diisi")
    LocalDate birthDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate deceasedDate;

    @Size(message = "Jumlah karakter maksimal 50", max = 50)
    String education;

    @Size(message = "Maksimal karakter 500", max = 10)
    String summary;
}