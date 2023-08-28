package com.audensiel.domain.operation;

import java.math.BigDecimal;

import static java.util.Objects.requireNonNull;

public class Deposit extends Operation {

    public Deposit(BigDecimal amount, BigDecimal balance) {
        super(requireNonNull(amount, "Deposited amount cannot be null."), requireNonNull(balance, "Account balance cannot be null.").add(amount));
    }

    @Override
    public String toString() {
        return "Deposit" + super.toString();
    }
}
