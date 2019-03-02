create database if not exists rec_db;

use rec_db;

drop table if exists recs_table;
create table recs_table(
    id int not null,
    barcode int not null,
    rec_type int not null,
    source int not null
);

drop table if exists location_map_table;
create table location_map_table(
    id int not null,
    municipality int not null,
    rec_type int not null
);