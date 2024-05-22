package com.winterhold.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookDto {
    @Size(message = "Maksimal Karakter 20", max = 20)
    @NotBlank(message = "Code harus diisi")
    String code;

    @Size(message = "Maksimal karakter 100", max = 100)
    @NotBlank(message = "Title harus diisi")
    String title;

    String categoryName;

    @NotNull(message = "Harus pilih author")
    Long authorId;

    Boolean isBorowwed;

    @Size(message = "Maksimal karakter 500", max = 500)
    String summary;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate releaseDate;

    Integer totalPage;
}
