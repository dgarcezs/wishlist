create table wishlist (id BIGSERIAL primary key,
                        customer_id varchar(255) not null);

create table wishlist_product (wishlist_id bigint not null,
                                product_id varchar(255) not null,
                                primary key (wishlist_id, product_id),
                                constraint fk_wishlist_product_wishlist 
                                  foreign key (wishlist_id) 
                                  references wishlist(id)
                                  on delete cascade);

create table config_properties (config_key varchar(255) primary key,
                                config_value varchar(255));