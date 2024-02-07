CREATE TABLE CLIENTE (
    id serial PRIMARY KEY,
    limite integer not null,
    saldo  integer not null
);

CREATE TABLE TRANSACAO (
    id serial PRIMARY KEY,
    client_id integer REFERENCES CLIENTE (id),
    realizado_em timestamp not null default now(),
    tipo char(1) not null,
    descricao varchar(10) not null,
    valor integer not null
);