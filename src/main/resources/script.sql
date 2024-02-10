CREATE TABLE IF NOT EXISTS public.CLIENTE (
    id serial PRIMARY KEY,
    limite integer not null,
    saldo  integer not null
);

CREATE TABLE IF NOT EXISTS public.TRANSACAO (
    id serial PRIMARY KEY,
    client_id integer REFERENCES CLIENTE (id),
    realizado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    tipo char(1) not null,
    descricao varchar(10) not null,
    valor integer not null
);


DO $$
BEGIN
    INSERT INTO public.CLIENTE (limite, saldo)
        VALUES (100000, 0),
            (80000, 0),
            (1000000, 0),
            (10000000, 0),
            (500000, 0);
END;
$$