create sequence order_item_seq start with 1 increment by 50;
create sequence product_order_seq start with 1 increment by 50;
create table order_item (price float(53) not null, id bigint not null, product_id bigint not null, productorder_id bigint, quantity bigint not null, category varchar(255), name varchar(255), primary key (id));
create table product_order (state smallint check (state between 0 and 4), id bigint not null, address varchar(255), username varchar(255), primary key (id));
alter table if exists order_item add constraint FKmlwv71ldlx0texojnvi0rfo3u foreign key (productorder_id) references product_order;