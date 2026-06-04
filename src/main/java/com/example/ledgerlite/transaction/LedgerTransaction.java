package com.example.ledgerlite.transaction;

import com.example.ledgerlite.account.Account;
import com.example.ledgerlite.category.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "ledger_transactions")
public class LedgerTransaction {
	@Id
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TransactionType type;

	@Column(nullable = false)
	private long amountCents;

	private String description;

	@Column(nullable = false)
	private Instant occurredAt;

	@Column(nullable = false)
	private Instant createdAt;

	protected LedgerTransaction() {
	}

	public LedgerTransaction(Account account, Category category, TransactionType type, long amountCents, String description,
			Instant occurredAt) {
		this.id = UUID.randomUUID();
		this.account = account;
		this.category = category;
		this.type = type;
		this.amountCents = amountCents;
		this.description = description;
		this.occurredAt = occurredAt == null ? Instant.now() : occurredAt;
		this.createdAt = Instant.now();
	}

	public UUID getId() {
		return id;
	}

	public Account getAccount() {
		return account;
	}

	public Category getCategory() {
		return category;
	}

	public TransactionType getType() {
		return type;
	}

	public long getAmountCents() {
		return amountCents;
	}

	public String getDescription() {
		return description;
	}

	public Instant getOccurredAt() {
		return occurredAt;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}
}
