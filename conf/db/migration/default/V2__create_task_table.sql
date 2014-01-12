create table tasks (
    id	SERIAL primary key,
    user_id	int8,
    name	varchar(1000),
    priority	int,
    is_done	bool DEFAULT false,
    time    int8 DEFAULT 0,
    limit_date	    timestamp,
    created_time	timestamp,
    updated_time	timestamp
);

create index tasks_idx_1 on tasks (
    user_id, is_done
);

create index tasks_idx_2 on tasks (
    user_id, limit_date
);
