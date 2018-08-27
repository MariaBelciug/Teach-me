package com.assist.internship.helpers;

import lombok.Data;

import java.util.List;

@Data
public class InternshipResponse {

    Boolean success;
    String message;
    List<ResponseObject> objects;

    public InternshipResponse(Boolean success, String message, List<ResponseObject> objects) {
        this.success = success;
        this.message = message;
        this.objects = objects;
    }
}

