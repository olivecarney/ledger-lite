package com.example.ledgerlite.account;

import com.example.ledgerlite.IntegrationTest;
import com.example.ledgerlite.transaction.LedgerTransactionService;
import com.example.ledgerlite.transaction.TransactionType;
import com.example.ledgerlite.user.AppUser;
import com.example.ledgerlite.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class AccountServiceTest extends IntegrationTest {
	@Autowired
	UserRepository users;

	@Autowired
	AccountService accounts;

	@Autowired
	LedgerTransactionService transactions;

	@Test
	void calculatesBalanceFromIncomeAndExpenseTransactions() {
		var user = users.save(new AppUser("Alex Ledger", "alex@example.com"));
		var account = accounts.create(user.getId(), "Main", "gbp");

		transactions.create(account.getId(), null, TransactionType.INCOME, 125_00, "Salary", null);
		transactions.create(account.getId(), null, TransactionType.EXPENSE, 45_25, "Groceries", null);

		assertThat(accounts.balanceCents(account.getId())).isEqualTo(79_75);
		assertThat(account.getCurrency()).isEqualTo("GBP");
	}
}
