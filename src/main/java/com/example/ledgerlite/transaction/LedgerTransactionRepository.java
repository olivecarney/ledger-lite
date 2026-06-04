package com.example.ledgerlite.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface LedgerTransactionRepository extends JpaRepository<LedgerTransaction, UUID> {
	List<LedgerTransaction> findByAccountIdOrderByCreatedAtDesc(UUID accountId);

	@Query("""
			select coalesce(sum(
				case when t.type = com.example.ledgerlite.transaction.TransactionType.INCOME
					then t.amountCents else -t.amountCents end
			), 0)
			from LedgerTransaction t
			where t.account.id = :accountId
			""")
	long calculateBalanceCents(@Param("accountId") UUID accountId);
}
