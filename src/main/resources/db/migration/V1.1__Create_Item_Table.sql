create table item (
	name character varying(255) not null,
	description character varying(255) not null,
	price integer not null,
	primary key(name)
);

create table inventoryitem(
	itemname character varying(255) not null,
	stock integer not null,
	foreign key(itemname) references item(name)
);

insert into item(name, description, price) values('NotebookComputer', 'Notebook PC for personal use', 1000);
insert into item(name, description, price) values('DesktopComputer', 'Desktop PC for personal use', 1000);
insert into inventoryitem(itemname, stock) values('NotebookComputer', 100);
insert into inventoryitem(itemname, stock) values('DesktopComputer', 100);

