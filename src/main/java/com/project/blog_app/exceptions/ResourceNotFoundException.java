package com.project.blog_app.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    String resourceName;
    String fieldName;
    long fieldValue;
    String userName;

    public ResourceNotFoundException(String resourceName,String fieldName,long fieldValue){
        
        super(String.format("%s not found with %s : %s", resourceName,fieldName,fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
    public ResourceNotFoundException(String resourceName,String fieldName,String userName){
        
        super(String.format("%s not found with %s : %s", resourceName,fieldName,userName));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.userName = userName;
    }
}
