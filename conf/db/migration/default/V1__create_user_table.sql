create table users (
  id    SERIAL  primary key,
  user_identifier   varchar(100),
  created_time  timestamp,
  updated_time  timestamp
);

create unique index users_index_1 on users (
    user_identifier
);