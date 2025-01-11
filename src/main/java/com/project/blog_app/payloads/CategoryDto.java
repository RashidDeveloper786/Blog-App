package com.project.blog_app.payloads;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

    private Integer categoryId;

    @NotNull
    @Size(min = 5 , max = 50)
    private String categoryTitle;

    @NotNull
    @Size(min = 10 , max = 200)
    private String categoryDesc;
}
