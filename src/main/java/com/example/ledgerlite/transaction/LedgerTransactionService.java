package com.example.ledgerlite.transaction;

import com.example.ledgerlite.account.AccountRepository;
import com.example.ledgerlite.category.Category;
import com.example.ledgerlite.category.CategoryRepository;
import com.example.ledgerlite.common.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class LedgerTransactionService {
	private final LedgerTransactionRepository transactions;
	private final AccountRepository accounts;
	private final CategoryRepository categories;

	LedgerTransactionService(LedgerTransactionRepository transactions, AccountRepository accounts,
			CategoryRepository categories) {
		this.transactions = transactions;
		this.accounts = accounts;
		this.categories = categories;
	}

	@Transactional
	public LedgerTransaction create(UUID accountId, UUID categoryId, TransactionType type, long amountCents,
			String description, Instant occurredAt) {
		if (amountCents <= 0) {
			throw new IllegalArgumentException("amountCents must be positive");
		}
		var account = accounts.findById(accountId)
				.orElseThrow(() -> new NotFoundException("Account not found: " + accountId));
		Category category = categoryId == null ? null : categories.findById(categoryId)
				.orElseThrow(() -> new NotFoundException("Category not found: " + categoryId));
		return transactions.save(new LedgerTransaction(account, category, type, amountCents, description, occurredAt));
	}

	@Transactional(readOnly = true)
	public List<LedgerTransaction> list(UUID accountId) {
		if (accountId == null) {
			return transactions.findAll();
		}
		if (!accounts.existsById(accountId)) {
			throw new NotFoundException("Account not found: " + accountId);
		}
		return transactions.findByAccountIdOrderByCreatedAtDesc(accountId);
	}
}
