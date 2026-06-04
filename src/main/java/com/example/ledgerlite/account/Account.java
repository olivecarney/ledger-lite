package com.example.ledgerlite.account;

import com.example.ledgerlite.user.AppUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "accounts")
public class Account {
	@Id
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private AppUser user;

	@Column(nullable = false, length = 120)
	private String name;

	@Column(nullable = false, length = 3)
	private String currency;

	@Column(nullable = false)
	private Instant createdAt;

	protected Account() {
	}

	public Account(AppUser user, String name, String currency) {
		this.id = UUID.randomUUID();
		this.user = user;
		this.name = name;
		this.currency = currency.toUpperCase();
		this.createdAt = Instant.now();
	}

	public UUID getId() {
		return id;
	}

	public AppUser getUser() {
		return user;
	}

	public String getName() {
		return name;
	}

	public String getCurrency() {
		return currency;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}
}
