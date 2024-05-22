package com.winterhold.dto.account;

import com.winterhold.validation.ComparePassword;
import com.winterhold.validation.UniqueUsername;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ComparePassword(message = "password tidak match dengan konfirmasinya")
public class RegisterDto {

    @UniqueUsername(message = "username sudah dipakai")
    @NotBlank(message = "Username harus diisi")
    @Size(max = 20, message = "username maksimal 20 karakter")
    private String username;

    @NotBlank(message = "Password harus diisi")
    @Size(min=8, max = 16, message = "Password hanya bisa 8 - 16 karakter")
    private String password;

    @NotBlank(message = "Mohon isi konfirmasi password")
    @Size(min=8, max = 16, message = "Password hanya bisa 8 - 16 karakter")
    private String passwordConfirmation;
}
