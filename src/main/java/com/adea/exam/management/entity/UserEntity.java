package com.adea.exam.management.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;


@Data
@NoArgsConstructor
@Entity
@Table(name = "USER")
public class UserEntity {

    @Id
    @Column(name = "LOGIN", length = 20)
    private String user;

    @Column(nullable = false, length = 65)
    private String password;

    @Column(nullable = false, name = "CLIENTE")
    private Integer client;

    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String name;
    @Column(name = "APELLIDO_PATERNO", nullable = false, length = 50)
    private String paternal;
    @Column(name = "APELLIDO_MATERNO", nullable = false, length = 50)
    private String maternal;

    @Column(length = 50)
    private String email;

    @Column(name = "FECHA_ALTA", insertable = false, updatable = false, columnDefinition = "timestamp(6) default current_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp startDate;

    @Column(name = "FECHA_BAJA")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp endDate;

    @Column(columnDefinition = "char(1) default 'A'")
    private char status;

    @Column(name = "INTENTOS", columnDefinition = "integer default 0")
    private Integer attempts;

    @Column(name = "FECHA_REVOCADO")
    @Temporal(TemporalType.DATE)
    private Date revokeDate;

    @Column(name = "FECHA_VIGENCIA")
    @Temporal(TemporalType.DATE)
    private Date validityDate;

    @Column(name = "NO_ACCESO")
    private Integer accessId;

    @Column
    private Integer area;

    @Column(name = "FECHA_MODIFICACION", insertable = false, updatable = false, columnDefinition = "timestamp(6) default current_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp updateDate;
}
