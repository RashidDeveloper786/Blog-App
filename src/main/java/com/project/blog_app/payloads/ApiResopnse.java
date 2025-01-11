package com.project.blog_app.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiResopnse {
    
    private String message;
    private boolean status;
}
