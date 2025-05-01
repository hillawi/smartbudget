create table if not exists transaction
(
    id              uuid primary key       default gen_random_uuid(),
    sequence_number varchar(255)  not null,
    amount          numeric(8, 2) not null,
    "type"          varchar(3)    not null,
    category        varchar(255)  not null default 'UNKNOWN',
    "date"          date          not null,
    created_at      timestamp     not null
);

create index if not exists transaction_idx_date on transaction ("date");

create table if not exists categorization
(
    id         bigserial primary key,
    input_text text         not null,
    category   varchar(255) not null,
    created_at timestamp    not null
);