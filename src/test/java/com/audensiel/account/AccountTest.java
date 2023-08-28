package com.audensiel.account;

import com.audensiel.domain.account.Account;
import com.audensiel.domain.operation.Deposit;
import com.audensiel.domain.operation.Operation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

class AccountTest {

    private Account account;

    @BeforeEach
    public void setUp() {
        account = new Account("1234567890", "Alpha Beta");
    }

    @Test
    public void testDeposit_ValidAmount_SuccessfulDeposit() {

        account.deposit(new BigDecimal(100));
        Assertions.assertEquals(new BigDecimal(100), account.getBalance());
        Assertions.assertEquals(1, account.getOperations().size());
    }

    @Test
    public void testDeposit_InvalidAmount_NoDepositPerformed() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> account.deposit(new BigDecimal(-100)));
        Assertions.assertEquals(BigDecimal.ZERO, account.getBalance());
        Assertions.assertTrue(account.getOperations().isEmpty());
    }

    @Test
    public void testWithdraw_ValidAmount_SuccessfulWithdrawal() throws Account.InsufficientFundsException {

        account.deposit(new BigDecimal(200));
        account.withdraw(new BigDecimal(100));
        Assertions.assertEquals(new BigDecimal(100), account.getBalance());
        Assertions.assertEquals(2, account.getOperations().size());
    }

    @Test
    public void testWithdraw_InsufficientFunds_ExceptionThrown() {

        account.deposit(new BigDecimal(100));
        Assertions.assertThrows(Account.InsufficientFundsException.class, () -> account.withdraw(new BigDecimal(200)));
        Assertions.assertEquals(new BigDecimal(100), account.getBalance());
        Assertions.assertEquals(1, account.getOperations().size());
    }

    @Test
    public void testOperationHistory_DepositAndWithdrawal_OperationsRecorded() throws Account.InsufficientFundsException {

        List<Operation> operations = new ArrayList<>();
        operations.add(account.deposit(new BigDecimal(200)));
        operations.add(account.withdraw(new BigDecimal(100)));
        operations.add(account.deposit(BigDecimal.ONE));
        operations.add(account.withdraw(BigDecimal.TEN));

        Assertions.assertEquals(operations, account.getOperations());

        for(int i = 1; i < operations.size(); i++) { // Check balance continuity
            Assertions.assertEquals(operations.get(i) instanceof Deposit ?
                            operations.get(i - 1).getBalance().add(operations.get(i).getAmount()):
                            operations.get(i - 1).getBalance().subtract(operations.get(i).getAmount()),
                    operations.get(i).getBalance());
        }
    }
}