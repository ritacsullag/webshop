create sequence revinfo_seq start with 1 increment by 50;
create table category_aud
(
    id      bigint  not null,
    rev     integer not null,
    revtype smallint,
    name    varchar(255),
    primary key (rev, id)
);
create table product_aud
(
    id          bigint  not null,
    price       float(53),
    rev         integer not null,
    revtype     smallint,
    category_id bigint,
    name        varchar(255),
    primary key (rev, id)
);
create table revinfo
(
    rev      integer not null,
    revtstmp bigint,
    primary key (rev)
);
alter table if exists category_aud
    add constraint FKc9m640crhsib2ws80um6xuk1w foreign key (rev) references revinfo;
alter table if exists product_aud
    add constraint FK9vwllld6jlw5xys1ay911oh1x foreign key (rev) references revinfo;
