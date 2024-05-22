package com.winterhold.dto.category;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRowDto implements Serializable {
    String name;
    Integer floor;
    String isle;
    String bay;
    Long totalBooks;
}