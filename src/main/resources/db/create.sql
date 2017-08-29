SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS song(
  id int PRIMARY KEY auto_increment,
  songname VARCHAR,
  genre VARCHAR,
  subgenre VARCHAR

);

CREATE TABLE IF NOT EXISTS band(
id int PRIMARY KEY auto_increment,
bandname VARCHAR,
songid int
);