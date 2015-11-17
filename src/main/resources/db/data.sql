insert into User (balance, birthday, discount, email, experience, lastBetDate, name, password, points, role, userRole) 
values (1000, '1984-06-10 10:27:57', 0, 'user@mail.ua', 0, null, 'Alex', '111111', 999, 'ROLE_USER', 0);
insert into User (balance, birthday, discount, email, experience, lastBetDate, name, password, points, role, userRole) 
values (10000, '1979-06-13 12:20:00', 0, 'member@mail.ua', 11, '2013-04-01 10:54:00', 'John', '111111', 0, 'ROLE_USER', 1);
insert into User (balance, birthday, discount, email, experience, lastBetDate, name, password, points, role, userRole) 
values (100000, '1982-06-13 09:27:57', 0.2, 'veteran@mail.ua', 120, '2013-05-11 01:21:00', 'Bill', '111111', 0, 'ROLE_USER', 2);
insert into User (balance, birthday, discount, email, experience, lastBetDate, name, password, points, role, userRole) 
values (100, '1991-06-13 12:20:00', 0, 'miser@mail.ua', 3, '2012-08-20 12:20:00', 'Miser', '111111', 0, 'ROLE_USER', 1);

insert into Lot (CURRENTBET_ID, description, endDate, imgPath, lotState, name, startPrice, user_id) values ( null, 'New with box', '2013-05-20 10:30:40', '/resources/userimg/rainboot.jpg', 1, 'Rain Boots', 69.99, 2);
insert into Lot (CURRENTBET_ID, description, endDate, imgPath, lotState, name, startPrice, user_id) values ( null, '4x4 big car', '2013-03-20 10:30:40', '/resources/userimg/dodge.jpg', 0, 'Car', 11000, 2);
insert into Lot (CURRENTBET_ID, description, endDate, imgPath, lotState, name, startPrice, user_id) values ( null, '2 wireles ir headphones', '2013-05-20 01:30:35', '/resources/userimg/headphones.jpg', 1, 'HEADPHONES', 99.45, 2);
insert into Lot (CURRENTBET_ID, description, endDate, imgPath, lotState, name, startPrice, user_id) values ( null, 'New iPhone', '2013-05-20 10:30:00', '/resources/userimg/iphone.jpg', 1, 'Phone', 720, 1);
insert into Lot (CURRENTBET_ID, description, endDate, imgPath, lotState, name, startPrice, user_id) values ( null, 'New with tags', '2013-07-20 12:35:10', '/resources/userimg/sunglasses.jpg', 1, 'Sunglasses', 190.5, 1);
insert into Lot (CURRENTBET_ID, description, endDate, imgPath, lotState, name, startPrice, user_id) values ( null, 'Dyson DC41 Animal Upright', '2013-04-20 11:30:40', '/resources/userimg/dc41.gif', 1, 'Vacuum Cleaner', 399.49, 2);
insert into Lot (CURRENTBET_ID, description, endDate, imgPath, lotState, name, startPrice, user_id) values ( null, 'MAC', '2013-07-20 12:35:10', '/resources/userimg/Apple.jpg', 1, 'PC', 1060.5, 1);
insert into Lot (CURRENTBET_ID, description, endDate, imgPath, lotState, name, startPrice, user_id) values ( null, 'Dell new model', '2013-09-09 19:05:14', '/resources/userimg/dell.jpg', 1, 'Laptop', 2450.0, 1);
insert into Lot (CURRENTBET_ID, description, endDate, imgPath, lotState, name, startPrice, user_id) values ( null, 'Canon EOS 6D', '2013-07-20 02:35:10', '/resources/userimg/Canon_EOS.jpg', 1, 'Digital SLR Camera', 1250.5, 1);
insert into Lot (CURRENTBET_ID, description, endDate, imgPath, lotState, name, startPrice, user_id) values ( null, 'Football ball', '2013-07-01 12:35:15', '/resources/userimg/ball.jpg', 1, 'Ball', 50.75, 1);
insert into Lot (CURRENTBET_ID, description, endDate, imgPath, lotState, name, startPrice, user_id) values ( null, 'Mens Huge Heavy Gold ring', '2013-07-20 03:35:11', '/resources/userimg/goldring.jpg', 1, 'Steel Ring', 150.5, 1);
insert into Lot (CURRENTBET_ID, description, endDate, imgPath, lotState, name, startPrice, user_id) values ( null, 'Coaster 202451Q', '2014-09-02 18:35:10', '/resources/userimg/queenbed.jpg', 1, 'Queen Bed', 250.00, 1);
insert into Lot (CURRENTBET_ID, description, endDate, imgPath, lotState, name, startPrice, user_id) values ( null, 'Solid Platinum, 1 Carat', '2013-05-02 20:12:50', '/resources/userimg/ering.jpg', 0, 'Engagement Ring', 12050.90, 2);

insert into Bet (betDate, betPrice, betState, lot_id, user_id) values ( '2013-03-13 12:20:00', 13000, 0, 13, 3);
insert into Bet (betDate, betPrice, betState, lot_id, user_id) values ( '2013-04-01 10:54:00', 750, 0, 4, 2);
insert into Bet (betDate, betPrice, betState, lot_id, user_id) values ( '2013-05-11 01:21:00', 200, 0, 5, 3);
insert into Bet (betDate, betPrice, betState, lot_id, user_id) values ( '2013-03-11 11:15:00', 12000, 2, 2, 3);

insert into Earnings (payment, paymentDate, USER_ID) values ( 10.5, '2013-02-11 04:22:00', 1);
insert into Earnings (payment, paymentDate, USER_ID) values ( 100, '2013-01-07 14:15:43', 2);
insert into Earnings (payment, paymentDate, USER_ID) values ( 1300, '2013-01-07 23:25:51', 1);
insert into Earnings (payment, paymentDate, USER_ID) values ( 340.22, '2013-01-14 18:17:03', 1);
insert into Earnings (payment, paymentDate, USER_ID) values ( 40.5, '2013-01-15 08:10:00', 1);
insert into Earnings (payment, paymentDate, USER_ID) values ( 550.49, '2012-12-05 12:40:00', 1);