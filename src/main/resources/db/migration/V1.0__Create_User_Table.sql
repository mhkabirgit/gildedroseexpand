create table users (
	username character varying(255) not null,
	password character varying(255) not null,
	enabled boolean not null,
	primary key(username)
);

create table authorities(
	username character varying(255) not null,
	authority character varying(255) not null,
	foreign key(username) references users(username)
);

insert into users(username, password, enabled) values('admin', '$2a$10$CMvnw3CnZggh6xEHmecSWuJwlySiOZo8FRUJLGR3x7rEkEGDXM/Lm', true);
insert into authorities(username, authority) values('admin', 'ADMIN');
insert into authorities(username, authority) values('admin', 'USER');
insert into users(username, password, enabled) values('user', '$2a$10$CMvnw3CnZggh6xEHmecSWuJwlySiOZo8FRUJLGR3x7rEkEGDXM/Lm', true);
insert into authorities(username, authority) values('user', 'USER');
