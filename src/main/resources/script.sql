CREATE TABLE CLIENTE (
    id bigint AUTO_INCREMENT PRIMARY KEY,
    limite bigint not null,
    saldo  bigint not null,
)

CREATE TABLE TRANSACAO (
    id bigint AUTO_INCREMENT PRIMARY KEY,
    client_id bigint REFERENCES CLIENTE (id),
    criado_em timestamp not null,
    tipo varchar(2) not null,
    descricao varchar(10) not null,
    valor bigint not null,
)


DO $$
BEGIN
    INSERT INTO CLIENTE (limite, saldo)
    VALUES (100000, 0),
           (80000, 0),
           (1000000, 0),
           (10000000, 0),
           (500000, 0)
END; $$