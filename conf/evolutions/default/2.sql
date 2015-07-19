# --- !Ups

CREATE SEQUENCE action_id_seq;
CREATE TABLE Actions(
    id bigint NOT NULL DEFAULT nextval('action_id_seq'),
    user_id bigint NOT NULL,
    item_id bigint NOT NULL,
    action_type varchar(50),
    FOREIGN KEY (user_id) REFERENCES Users(id),
    FOREIGN KEY (item_id) REFERENCES Items(id) on delete cascade,
    PRIMARY KEY (id)
);

# --- !Downs

DROP TABLE Actions;

DROP SEQUENCE action_id_seq;
