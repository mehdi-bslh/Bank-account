package com.audensiel.domain.operation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.StringJoiner;

import static java.util.Objects.requireNonNull;

public abstract class Operation {

    private final LocalDateTime timestamp = LocalDateTime.now();

    private final BigDecimal amount;

    private final BigDecimal balance;

    public Operation(BigDecimal amount, BigDecimal balance) {
        this.amount = amount;
        this.balance = balance;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", "[", "]")
                .add("timestamp=" + timestamp)
                .add("amount=" + amount)
                .add("balance=" + balance)
                .toString();
    }
}
