package com.adea.exam.management.controller.beans;

public record User(String login,
                   String name,
                   Number startDate,
                   Number endDate,
                   char status) {
}
