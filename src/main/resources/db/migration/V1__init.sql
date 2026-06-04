create table app_users (
    id uuid primary key,
    name varchar(120) not null,
    email varchar(255) not null unique,
    created_at timestamptz not null
);

create table categories (
    id uuid primary key,
    name varchar(80) not null unique,
    created_at timestamptz not null
);

create table accounts (
    id uuid primary key,
    user_id uuid not null references app_users(id),
    name varchar(120) not null,
    currency varchar(3) not null,
    created_at timestamptz not null,
    unique (user_id, name)
);

create table ledger_transactions (
    id uuid primary key,
    account_id uuid not null references accounts(id),
    category_id uuid references categories(id),
    type varchar(16) not null,
    amount_cents bigint not null check (amount_cents > 0),
    description varchar(255),
    occurred_at timestamptz not null,
    created_at timestamptz not null,
    check (type in ('INCOME', 'EXPENSE'))
);

create index idx_ledger_transactions_account_created_at
    on ledger_transactions(account_id, created_at desc);
