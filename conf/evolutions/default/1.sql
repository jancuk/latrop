# Users schema
 
# --- !Ups
 
CREATE TABLE User (
    email varchar(255) NOT NULL,
    PRIMARY KEY (email)
);
 
# --- !Downs
 
DROP TABLE User;
