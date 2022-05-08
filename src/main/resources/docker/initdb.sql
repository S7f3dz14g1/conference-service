CREATE EXTENSION pgcrypto;
SELECT gen_random_uuid();

CREATE TABLE IF NOT EXISTS Users(
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    login    varchar(30) not null,
    email varchar (30)not null,
    UNIQUE(email)
    );

CREATE TABLE IF NOT EXISTS Prelection(
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    times    varchar(30) not null,
    title varchar (30)not null,
    subject  varchar (30)not null
    );

CREATE TABLE IF NOT EXISTS Reservations(
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id    uuid not null,
    prelection_id uuid not null,
    constraint fk_tehnician
        foreign key(user_id)
            references Users(id),
    constraint fk_tehnician
        foreign key(prelection_id)
            references Prelection(id),
    );

INSERT INTO Users(login,email)
values ('S7f3dz14g1','krystianszwedo@wp.pl');
INSERT INTO Users(login,email)
values ('antek','ant@wp.pl');

INSERT INTO Users(times,subject,title)
values ('10.00 - 11-45','First','Java od zera.');
INSERT INTO Users(times,subject,title)
values ('12.00 - 13-45','First','Co to jest Hiberante');
INSERT INTO Users(times,subject,title)
values ('14.00 - 15-45','First','Podstawy springa');

INSERT INTO Users(times,subject,title)
values ('10.00 - 11-45','Second','C# od zera.');
INSERT INTO Users(times,subject,title)
values ('12.00 - 13-45','Second','C vs C++');
INSERT INTO Users(times,subject,title)
values ('14.00 - 15-45','Second','');

INSERT INTO Users(times,subject,title)
values ('10.00 - 11-45','Third','Python od zera.');
INSERT INTO Users(times,subject,title)
values ('12.00 - 13-45','Third','Tworzymy projekt MVC');
INSERT INTO Users(times,subject,title)
values ('14.00 - 15-45','Third','Co to jest django');