package com.spacer.rinhaapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "CLIENTE")
public final class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer limite;
    private Integer saldo;

    public Cliente() {
    }

    public Cliente(Integer id, Integer limite, Integer saldo) {
        this.id = id;
        this.limite = limite;
        this.saldo = saldo;
    }

    public Integer getId() {
        return id;
    }

    public Integer getLimite() {
        return limite;
    }

    public Integer getSaldo() {
        return saldo;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", limite=" + limite +
                ", saldo=" + saldo +
                '}';
    }
}
