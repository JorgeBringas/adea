package com.adea.exam.management.controller.beans;

public record UserRecord(
        String login,
        String password,
        String name,
        String paternal,
        String maternal,
        Number client,
        Number startDate,
        Number endDate,
        String email,
        Number area,
        Number access,
        char status) {
}
