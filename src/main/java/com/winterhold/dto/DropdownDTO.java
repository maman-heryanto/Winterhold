package com.winterhold.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DropdownDTO {
    private Object value;
    private String text;

    public static List<DropdownDTO> getRoleDropdown(){
        var roleDropdown = new ArrayList<DropdownDTO>();
        roleDropdown.add(new DropdownDTO("Administrator", "Administrator"));
        roleDropdown.add(new DropdownDTO("Finance", "Finance"));
        roleDropdown.add(new DropdownDTO("Salesman", "Salesman"));

        return roleDropdown;
    }
}
