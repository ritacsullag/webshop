create sequence category_seq start with 1 increment by 50;
create sequence product_seq start with 1 increment by 50;
create table category
(
    id   bigint not null,
    name varchar(255),
    primary key (id)
);
create table product
(
    id          bigint    not null,
    price       float(53) not null,
    category_id bigint,
    name        varchar(255),
    primary key (id)
);
alter table if exists product
    add constraint FK1mtsbur82frn64de7balymq9s foreign key (category_id) references category;
