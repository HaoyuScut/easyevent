DROP TABLE if exists tb_event;
DROP TABLE if exists tb_user;
CREATE TABLE tb_event (
    id int auto_increment,
    title varchar(100) not null,
    description varchar(255) not null ,
    price float not null,
    date datetime not null ,
    creator_id int not null ,
    primary key (id),
    constraint fk_created_id foreign key(creator_id) references tb_user(id)
);

CREATE TABLE tb_user (
    id int auto_increment,
    email varchar(100) not null ,
    password varchar(100) not null,
    primary key (id)
);
