package com.example.ledgerlite.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.Instant;
import java.util.UUID;

public final class AccountDtos {
	private AccountDtos() {
	}

	public record CreateAccountRequest(
			@NotNull UUID userId,
			@NotBlank String name,
			@NotBlank @Pattern(regexp = "^[A-Za-z]{3}$", message = "must be a 3-letter currency code") String currency) {
	}

	public record AccountResponse(UUID id, UUID userId, String name, String currency, Instant createdAt) {
		static AccountResponse from(Account account) {
			return new AccountResponse(account.getId(), account.getUser().getId(), account.getName(), account.getCurrency(),
					account.getCreatedAt());
		}
	}

	public record BalanceResponse(UUID accountId, String currency, long balanceCents) {
	}
}
