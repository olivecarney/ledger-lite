package com.example.ledgerlite.account;

import com.example.ledgerlite.common.NotFoundException;
import com.example.ledgerlite.transaction.LedgerTransactionRepository;
import com.example.ledgerlite.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {
	private final AccountRepository accounts;
	private final UserRepository users;
	private final LedgerTransactionRepository transactions;

	AccountService(AccountRepository accounts, UserRepository users, LedgerTransactionRepository transactions) {
		this.accounts = accounts;
		this.users = users;
		this.transactions = transactions;
	}

	@Transactional
	public Account create(UUID userId, String name, String currency) {
		var user = users.findById(userId).orElseThrow(() -> new NotFoundException("User not found: " + userId));
		return accounts.save(new Account(user, name, currency));
	}

	@Transactional(readOnly = true)
	public List<Account> list() {
		return accounts.findAll();
	}

	@Transactional(readOnly = true)
	public Account get(UUID id) {
		return accounts.findById(id).orElseThrow(() -> new NotFoundException("Account not found: " + id));
	}

	@Transactional(readOnly = true)
	public long balanceCents(UUID accountId) {
		get(accountId);
		return transactions.calculateBalanceCents(accountId);
	}
}
