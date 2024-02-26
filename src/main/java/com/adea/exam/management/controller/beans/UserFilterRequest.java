package com.adea.exam.management.controller.beans;

import lombok.Data;

@Data
public class UserFilterRequest {

    private String name;
    private long date1;
    private long date2;
    private String status;

}
