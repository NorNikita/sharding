create table userinfo (
    id serial not null primary key,
    name varchar(255) default null,
    days bigint default null,
    uuid varchar(255) default null
);