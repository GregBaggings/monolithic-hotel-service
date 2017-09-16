INSERT INTO hotels (id,name,country,city,address,lat,lon,minprice) VALUES ('1','Hotel1','Hungary','Budapest','Example Street 1.','47.49801','19.03991','500000');
INSERT INTO hotels (id,name,country,city,address,lat,lon,minprice) VALUES ('2','Hotel2','Hungary','Szeged','Example Street 1.','46.253','20.14824','250000');
INSERT INTO hotels (id,name,country,city,address,lat,lon,minprice) VALUES ('3','Hotel3','USA','New York','Example Street 1.','40.730610','-73.935242','1000000');
INSERT INTO hotels (id,name,country,city,address,lat,lon,minprice) VALUES ('4','Hotel4','Hungary','Pécs','Example Street 1.','46.08333','18.23333','2000');
INSERT INTO hotels (id,name,country,city,address,lat,lon,minprice) VALUES ('5','Hotel5','Hungary','Budapest','Example Street 2.','47.49801','19.03991','1000');

INSERT INTO prices (hotelid,roomid,roomname,price) VALUES ('1','1','Double Room','5000');
INSERT INTO prices (hotelid,roomid,roomname,price) VALUES ('1','2','Single Room','2500');
INSERT INTO prices (hotelid,roomid,roomname,price) VALUES ('2','3','Elite Room','10000');