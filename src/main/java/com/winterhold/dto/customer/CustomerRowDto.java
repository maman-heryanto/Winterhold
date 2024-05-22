package com.winterhold.dto.customer;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRowDto{
    String membershipNumber;
    String fullName;
    LocalDate membershipExpireDate;
}