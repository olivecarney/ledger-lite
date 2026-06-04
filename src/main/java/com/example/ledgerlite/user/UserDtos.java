package com.example.ledgerlite.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;
import java.util.UUID;

public final class UserDtos {
	private UserDtos() {
	}

	public record CreateUserRequest(@NotBlank String name, @Email @NotBlank String email) {
	}

	public record UserResponse(UUID id, String name, String email, Instant createdAt) {
		static UserResponse from(AppUser user) {
			return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt());
		}
	}
}
