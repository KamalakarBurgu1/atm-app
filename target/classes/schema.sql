 create table account(id integer not null, balance integer not null, overdraft integer not null, primary key (id));

	create table atmamount (
       id integer not null,
        fifty integer not null,
        five integer not null,
        location varchar(255),
        ten integer not null,
        twenty integer not null,
        primary key (id)
    );

	create table user (
       id integer not null,
        account_number varchar(255) not null,
        pin varchar(255) not null,
        status varchar(255) not null,
        account_id integer,
        primary key (id)
    );

	create table user_roles (
       user_id integer not null,
        role varchar(255) not null,
        primary key (user_id, role)
    );

	alter table user
       add constraint FKc3b4xfbq6rbkkrddsdum8t5f0
       foreign key (account_id)
       references account;

	   alter table user_roles
       add constraint FK55itppkw3i07do3h7qoclqd4k
       foreign key (user_id)
       references user;