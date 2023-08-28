package com.audensiel.domain.account;

import com.audensiel.domain.operation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class Account {

    private final String accountNumber;

    private final String accountHolder;

    private BigDecimal balance = BigDecimal.ZERO;

    private final List<Operation> operations = new ArrayList<>();

    public Account(String accountNumber, String accountHolder) {
        this.accountNumber = requireNonNull(accountNumber, "Account number must be defined.");
        this.accountHolder = requireNonNull(accountHolder, "Account holder must be identified.");
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public static void checkPositiveAmount(BigDecimal amount) {

        if(requireNonNull(amount, "Amount cannot be null.").compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive.");
        }
    }

    public void commitTransaction(Operation operation) {

        operations.add(operation);
        balance = operation.getBalance();
    }

    public Operation deposit(BigDecimal amount) {

        checkPositiveAmount(amount);

        Operation operation = new Deposit(amount, balance);

        commitTransaction(operation);

        return operation;
    }

    public static class InsufficientFundsException extends Exception {}

    public Operation withdraw(BigDecimal amount) throws InsufficientFundsException {

        checkPositiveAmount(amount);

        if (balance.compareTo(amount) < 0) {
            throw new InsufficientFundsException();
        }

        Operation operation = new Withdrawal(amount, balance);

        commitTransaction(operation);

        return operation;
    }

    public void displayHistory() {
        operations.forEach(System.out::println);
    }
}
