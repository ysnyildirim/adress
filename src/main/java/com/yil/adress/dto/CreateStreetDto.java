package com.yil.adress.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateStreetDto {
    @NotBlank
    @Length(min = 1, max = 100)
    private String name;
    @NotNull
    private Long districtId;
    @Length(min = 1, max = 5)
    private String postCode;
}
