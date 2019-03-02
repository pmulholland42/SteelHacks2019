create database if not exists rec_db;

use rec_db;

create table if not exists recs_table(
    barcode int not null,
    rec_type varchar(10) not null,
    source varchar(10) not null
);

create table if not exists location_map_table(
    municipality varchar(50) not null,
    rec_type varchar(10) not null
);