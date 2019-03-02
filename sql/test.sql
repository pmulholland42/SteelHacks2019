create database if not exists test_db;

use test_db;

create table if not exists test_table(
    test_col varchar(255) not null
);