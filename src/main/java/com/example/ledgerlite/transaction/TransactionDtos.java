package com.example.ledgerlite.transaction;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.UUID;

public final class TransactionDtos {
	private TransactionDtos() {
	}

	public record CreateTransactionRequest(
			@NotNull UUID accountId,
			UUID categoryId,
			@NotNull TransactionType type,
			@Positive long amountCents,
			@Size(max = 255) String description,
			Instant occurredAt) {
	}

	public record TransactionResponse(UUID id, UUID accountId, UUID categoryId, TransactionType type, long amountCents,
			String description, Instant occurredAt, Instant createdAt) {
		static TransactionResponse from(LedgerTransaction transaction) {
			UUID categoryId = transaction.getCategory() == null ? null : transaction.getCategory().getId();
			return new TransactionResponse(transaction.getId(), transaction.getAccount().getId(), categoryId,
					transaction.getType(), transaction.getAmountCents(), transaction.getDescription(),
					transaction.getOccurredAt(), transaction.getCreatedAt());
		}
	}
}
