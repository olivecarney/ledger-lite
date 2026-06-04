package com.example.ledgerlite.account;

import com.example.ledgerlite.account.AccountDtos.AccountResponse;
import com.example.ledgerlite.account.AccountDtos.BalanceResponse;
import com.example.ledgerlite.account.AccountDtos.CreateAccountRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
class AccountController {
	private final AccountService accounts;

	AccountController(AccountService accounts) {
		this.accounts = accounts;
	}

	@PostMapping
	AccountResponse create(@Valid @RequestBody CreateAccountRequest request) {
		return AccountResponse.from(accounts.create(request.userId(), request.name(), request.currency()));
	}

	@GetMapping
	List<AccountResponse> list() {
		return accounts.list().stream().map(AccountResponse::from).toList();
	}

	@GetMapping("/{id}")
	AccountResponse get(@PathVariable UUID id) {
		return AccountResponse.from(accounts.get(id));
	}

	@GetMapping("/{id}/balance")
	BalanceResponse balance(@PathVariable UUID id) {
		var account = accounts.get(id);
		return new BalanceResponse(id, account.getCurrency(), accounts.balanceCents(id));
	}
}
