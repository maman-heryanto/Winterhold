package com.winterhold.dto.customer;

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
public class UpdateCustomerDto {
    @Size(message = "Maksimal 20 karakter", max = 20)
    @NotBlank(message = "Membership Number Harus Diisi")
    String membershipNumber;

    @Size(message = "Maksimal 50 karakter", max = 50)
    @NotBlank(message = "First Name Harus Diisi")
    String firstName;

    @Size(message = "Maksimal 50 karakter", max = 50)
    String lastName;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthDate;

    @NotNull
    String gender;

    String phone;
    String address;
    LocalDate membershipExpireDate;
}
