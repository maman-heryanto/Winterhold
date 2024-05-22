package com.winterhold.dto.category;

import com.winterhold.validation.UniqueCategoryName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@UniqueCategoryName(message = "Category Name Sudah Ada")
public class UpsertCategoryDto{
    @Size
    @NotBlank(message = "Name harus diisi")
    String name;
    @NotNull(message = "Floor harus di isi")
    Integer floor;
    @Size(message = "Maksimal 10 karakter", max = 10)
    @NotBlank(message = "Isle harus diisi")
    String isle;
    @Size(max = 10)
    @NotBlank(message = "Bay Harus di isi")
    String bay;
}