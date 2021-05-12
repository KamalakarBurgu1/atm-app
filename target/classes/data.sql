insert into account(balance, overdraft, id) values(800, 200, 0);
insert into account(balance, overdraft, id) values(1230, 150, 1);


insert into user(account_id, account_number, pin, status, id) values(0, '123456789', '$2y$12$yTiZZYeSor/9ZNY6K12wturOLJRwTJ5BCaHBa810aZnt7joNPYa7.', 'NonBlock', 0);
insert into user(account_id, account_number, pin, status, id) values(0, '123456', '$2y$12$OcjubwExpf9OkOf0xhqhgux.GTxRKkSP63/BhXC4OmOErT32suvt6', 'NonBlock', 1);
insert into user(account_id, account_number, pin, status, id) values(1, '987654321', '$2y$12$35ZDoxnxpTKrSwSTW7lDS.Xq7Ieu5FkRjgLzPSqZenXsN/cC8cnG.', 'NonBlock', 2);



insert into user_roles(user_id, role) values(0, 'ROLE_USER');
insert into user_roles(user_id, role) values(1, 'ROLE_ADMIN');
insert into user_roles(user_id, role) values(2, 'ROLE_USER');








insert into atmamount(fifty, five, location, ten, twenty, id) values(10, 20, 'ifsc', 30, 30, 0);