create database sphere0;
use sphere0;
create table userinfo (
    id int(11) not null auto_increment primary key,
    name varchar(255) default null,
    days int(11) default null
);