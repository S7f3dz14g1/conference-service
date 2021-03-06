CREATE EXTENSION pgcrypto;
SELECT gen_random_uuid();

CREATE TABLE IF NOT EXISTS Users(
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    login    varchar(30) not null,
    email varchar (30)not null,
    UNIQUE(login)
    );

CREATE TABLE IF NOT EXISTS Lecture(
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    times    varchar(30) not null,
    thematic_paths_title varchar (30)not null,
    subject  varchar (30)not null
    );

CREATE TABLE IF NOT EXISTS Reservations(
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id    uuid not null,
    lecture_id uuid not null,
    constraint fk_user
        foreign key(user_id)
            references Users(id),
    constraint fk_prelection
        foreign key(lecture_id)
            references Lecture(id)
    );

INSERT INTO Users(login,email)
values ('S7f3dz14g1','krystianszwedo@wp.pl');
INSERT INTO Users(login,email)
values ('antek','ant@wp.pl');
INSERT INTO Users(login,email)
values ('tomasz','tomasz@wp.pl');
INSERT INTO Users(login,email)
values ('lucek','lucek@wp.pl');
INSERT INTO Users(login,email)
values ('mrozu','mrozu@wp.pl');
INSERT INTO Users(login,email)
values ('y','y@wp.pl');

INSERT INTO Lecture(times,thematic_paths_title,subject)
values ('10.00 - 11-45','First','Java od zera.');
INSERT INTO Lecture(times,thematic_paths_title,subject)
values ('12.00 - 13-45','First','Co to jest Hiberante');
INSERT INTO Lecture(times,thematic_paths_title,subject)
values ('14.00 - 15-45','First','Podstawy springa');

INSERT INTO Lecture(times,thematic_paths_title,subject)
values ('10.00 - 11-45','Second','C# od zera.');
INSERT INTO Lecture(times,thematic_paths_title,subject)
values ('12.00 - 13-45','Second','C vs C++');
INSERT INTO Lecture(times,thematic_paths_title,subject)
values ('14.00 - 15-45','Second','Piszemy czysty kod');

INSERT INTO Lecture(times,thematic_paths_title,subject)
values ('10.00 - 11-45','Third','Python od zera.');
INSERT INTO Lecture(times,thematic_paths_title,subject)
values ('12.00 - 13-45','Third','Tworzymy projekt MVC');
INSERT INTO Lecture(times,thematic_paths_title,subject)
values ('14.00 - 15-45','Third','Co to jest django?');

INSERT INTO Reservations(user_id,lecture_id)
values ((SELECT id FROM Users where login = 'antek'),
    (SELECT id FROM Lecture where subject = 'Co to jest django?'));

INSERT INTO Reservations(user_id,lecture_id)
values ((SELECT id FROM Users where login = 'S7f3dz14g1'),
    (SELECT id FROM Lecture where subject = 'Python od zera.'));

INSERT INTO Reservations(user_id,lecture_id)
values ((SELECT id FROM Users where login = 'tomasz'),
        (SELECT id FROM Lecture where subject = 'Python od zera.'));

INSERT INTO Reservations(user_id,lecture_id)
values ((SELECT id FROM Users where login = 'lucek'),
        (SELECT id FROM Lecture where subject = 'Python od zera.'));

INSERT INTO Reservations(user_id,lecture_id)
values ((SELECT id FROM Users where login = 'mrozu'),
        (SELECT id FROM Lecture where subject = 'Python od zera.'));

INSERT INTO Reservations(user_id,lecture_id)
values ((SELECT id FROM Users where login = 'y'),
        (SELECT id FROM Lecture where subject = 'Python od zera.'));

