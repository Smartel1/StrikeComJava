create table favourite_conflicts
(
    user_id  integer not null
        constraint fk_7349a778a76ed395
            references users
            on delete cascade,
    conflict_id integer not null
        constraint fk_7349a77871f7e88b
            references conflicts
            on delete cascade,
    constraint favourite_conflicts_pkey
        primary key (user_id, conflict_id)
);
create index idx_1349a77871f7e88b
    on favourite_conflicts (conflict_id);

create index idx_1349a778a76ed395
    on favourite_conflicts (user_id);