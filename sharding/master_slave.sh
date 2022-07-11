#docker run -d -e MYSQL_ROOT_PASSWORD=123456 --name mysql-master -v $HOME/sharding/sharding/db_conf/master.cnf:/etc/mysql/my.cnf -p 3306:3306 mysql &&\
#docker run -d -e MYSQL_ROOT_PASSWORD=123456 --name mysql-slave -v $HOME/sharding/sharding/db_conf/slave.cnf:/etc/mysql/my.cnf -p 3307:3306 mysql

docker run -d -e MYSQL_ROOT_PASSWORD=123456 --name mysql-master -p 3306:3306 mysql &&\
docker run -d -e MYSQL_ROOT_PASSWORD=123456 --name mysql-slave -p 3307:3306 mysql

#
#mysql> create database sphere0;
#Query OK, 1 row affected (0.02 sec)
#
#mysql> use sphere0;
#Database changed
#mysql> create table userinfo0 (id int(11) not null primary key, name varchar(255) default null);