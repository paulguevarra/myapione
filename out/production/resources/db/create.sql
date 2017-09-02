SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS song(
  id int PRIMARY KEY auto_increment,
  songname VARCHAR,
  genre VARCHAR,
  subgenre VARCHAR,
  havesoldeawk INTEGER,
  publishedrank VARCHAR

);
CREATE TABLE IF NOT EXISTS writer(
id int PRIMARY KEY auto_increment,
writername VARCHAR
);

CREATE TABLE IF NOT EXISTS band(
id int PRIMARY KEY auto_increment,
bandname VARCHAR,
songid int
);

CREATE TABLE IF NOT EXISTS song_writer (
    id int PRIMARY KEY auto_increment,
    songid INTEGER,
    writerid INTEGER
);