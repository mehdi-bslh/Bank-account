package com.audensiel.domain.operation;

import java.math.BigDecimal;

import static java.util.Objects.requireNonNull;

public class Withdrawal extends Operation {

    public Withdrawal(BigDecimal amount, BigDecimal balance) {
        super(requireNonNull(amount, "Withdrawed amount cannot be null."), requireNonNull(balance, "Account balance cannot be null.").subtract(amount));
    }

    @Override
    public String toString() {
        return "Withdrawal" + super.toString();
    }
}
