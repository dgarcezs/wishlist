begin transaction;

insert into config_properties (config_key, config_value )
values ('max-products-per-wishlist', 15);

commit transaction;