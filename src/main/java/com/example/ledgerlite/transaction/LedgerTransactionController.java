package com.example.ledgerlite.transaction;

import com.example.ledgerlite.transaction.TransactionDtos.CreateTransactionRequest;
import com.example.ledgerlite.transaction.TransactionDtos.TransactionResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
class LedgerTransactionController {
	private final LedgerTransactionService transactions;

	LedgerTransactionController(LedgerTransactionService transactions) {
		this.transactions = transactions;
	}

	@PostMapping
	TransactionResponse create(@Valid @RequestBody CreateTransactionRequest request) {
		return TransactionResponse.from(transactions.create(request.accountId(), request.categoryId(), request.type(),
				request.amountCents(), request.description(), request.occurredAt()));
	}

	@GetMapping
	List<TransactionResponse> list(@RequestParam(required = false) UUID accountId) {
		return transactions.list(accountId).stream().map(TransactionResponse::from).toList();
	}
}
