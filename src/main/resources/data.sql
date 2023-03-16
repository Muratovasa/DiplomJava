create schema if not exists diplom;

insert into users(login, password)
values ('muratova@yandex.ru', '1213');
insert into users(login, password)
values ('ignatova@yandex.ru', '100500');
insert into users(login, password)
values ('sama@yandex.ru', '$10000');

insert into user_entity_roles(user_entity_id, roles)
values (1, 'ROLE_ADMIN');
insert into user_entity_roles(user_entity_id, roles)
values (1, 'ROLE_WRITE');
insert into user_entity_roles(user_entity_id, roles)
values (2, 'ROLE_WRITE');
insert into user_entity_roles(user_entity_id, roles)
values (3, 'ROLE_READ');