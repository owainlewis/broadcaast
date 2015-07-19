# Conversations schema

# --- !Ups

CREATE SEQUENCE user_id_seq;
CREATE TABLE Users(
    id bigint NOT NULL DEFAULT nextval('user_id_seq'),
    username varchar NOT NULL,
    email varchar NOT NULL,
    password varchar NOT NULL,
    token varchar,
    created TIMESTAMP WITH TIME ZONE NOT NULL,
    UNIQUE(username),
    UNIQUE(email),
    PRIMARY KEY (id)
);

CREATE SEQUENCE channel_id_seq;
CREATE TABLE Channels(
    id bigint NOT NULL DEFAULT nextval('channel_id_seq'),
    title varchar NOT NULL,
    context varchar,
    locked boolean NOT NULL DEFAULT true,
    isPublic boolean NOT NULL DEFAULT true,
    creator bigint NOT NULL,
    created TIMESTAMP WITH TIME ZONE NOT NULL,
    FOREIGN KEY (creator) REFERENCES Users(id),
    PRIMARY KEY (id)
);

CREATE TABLE Subscribers(
    channel_id bigint NOT NULL,
    user_id bigint NOT NULL
);

CREATE TABLE Members(
    channel_id bigint NOT NULL,
    user_id bigint NOT NULL
);

CREATE INDEX channel_id_index ON Channels USING btree(id);

CREATE SEQUENCE item_id_seq;
CREATE TABLE Items(
    id bigint NOT NULL DEFAULT nextval('item_id_seq'),
    channel_id bigint NOT NULL,
    body text,
    item_type int NOT NULL DEFAULT 1,
    user_id bigint,
    created TIMESTAMP WITH TIME ZONE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(id),
    FOREIGN KEY (channel_id) REFERENCES Channels(id),
    PRIMARY KEY (id)
);

CREATE INDEX item_id_index ON Items USING btree(id);
CREATE INDEX item_channel_id_index ON Items USING btree(channel_id);

# --- !Downs

DROP TABLE Users;
DROP SEQUENCE user_id_seq;

DROP TABLE Channels;
DROP SEQUENCE channel_id_seq;

DROP TABLE Subscribers;
DROP TABLE Members;

DROP TABLE Items;
DROP SEQUENCE item_id_seq;