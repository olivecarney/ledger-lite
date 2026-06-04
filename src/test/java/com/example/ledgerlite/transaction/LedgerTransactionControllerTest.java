package com.example.ledgerlite.transaction;

import com.example.ledgerlite.IntegrationTest;
import com.example.ledgerlite.account.AccountService;
import com.example.ledgerlite.user.AppUser;
import com.example.ledgerlite.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class LedgerTransactionControllerTest extends IntegrationTest {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	UserRepository users;

	@Autowired
	AccountService accounts;

	@Test
	void rejectsNonPositiveMoneyAmounts() throws Exception {
		var user = users.save(new AppUser("Taylor Ledger", "taylor@example.com"));
		var account = accounts.create(user.getId(), "Spending", "USD");

		mockMvc.perform(post("/transactions")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
								  "accountId": "%s",
								  "type": "EXPENSE",
								  "amountCents": 0,
								  "description": "Invalid"
								}
								""".formatted(account.getId())))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.detail").value("amountCents must be greater than 0"));
	}
}
