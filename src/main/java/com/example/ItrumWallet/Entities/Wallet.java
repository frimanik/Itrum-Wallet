package com.example.ItrumWallet.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class Wallet {
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID valletId;
    private BigDecimal balance;
    public Wallet(UUID valletId, BigDecimal balance) {
        this.valletId = valletId;
        this.balance = balance;
    }
    public Wallet() {
    }

    public UUID getValletId() {
        return valletId;
    }

    public void setValletId(UUID valletId) {
        this.valletId = valletId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
