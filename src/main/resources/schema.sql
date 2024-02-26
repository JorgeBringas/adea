create table user (
primary key (login),
login varchar(20) not null,
password varchar(65) not null,
nombre varchar(50) not null,
apellido_paterno varchar(50) not null,
apellido_materno varchar(50) not null,
email varchar(50),
intentos integer default 0,
status char(1) default 'A',
cliente integer not null,
no_acceso integer,
area NUMERIC(4,0),
fecha_alta timestamp(6) default current_timestamp,
fecha_baja timestamp(6),
fecha_revocado date,
fecha_vigencia date,
fecha_modificacion timestamp(6) default current_timestamp
);