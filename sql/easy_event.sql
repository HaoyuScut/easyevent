DROP TABLE if exists event;
CREATE TABLE event (
    id int auto_increment,
    title varchar(100) not null,
    description varchar(255) not null ,
    price float not null,
    date datetime not null ,
    primary key (id)
)
