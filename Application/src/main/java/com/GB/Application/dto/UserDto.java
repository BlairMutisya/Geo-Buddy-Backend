package com.GB.Application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String phoneNumber;
//    private List<PetDto> pets;
//    private List<ChildDto> children;
//    private List<LuggageDto> luggages;
}

