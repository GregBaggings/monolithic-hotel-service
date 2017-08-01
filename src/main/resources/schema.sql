CREATE TABLE hotels
(
id int NOT NULL IDENTITY,
name varchar(50),
country varchar(50),
city varchar(50),
address varchar(100),
lat decimal(9,6),
lon decimal(9,6)
);

CREATE TABLE prices
(
hotel_id int NOT NULL,
room_id int NOT NULL IDENTITY,
room_name varchar(50),
price int,
constraint FK_HOTELID FOREIGN KEY (hotel_id) references hotels (id)
);