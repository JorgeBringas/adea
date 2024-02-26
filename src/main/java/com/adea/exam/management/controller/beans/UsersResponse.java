package com.adea.exam.management.controller.beans;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class UsersResponse extends Response {
    private List<User> users;
}
