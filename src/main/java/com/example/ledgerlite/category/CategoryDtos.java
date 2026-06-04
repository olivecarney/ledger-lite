package com.example.ledgerlite.category;

import jakarta.validation.constraints.NotBlank;

import java.time.Instant;
import java.util.UUID;

public final class CategoryDtos {
	private CategoryDtos() {
	}

	public record CreateCategoryRequest(@NotBlank String name) {
	}

	public record CategoryResponse(UUID id, String name, Instant createdAt) {
		static CategoryResponse from(Category category) {
			return new CategoryResponse(category.getId(), category.getName(), category.getCreatedAt());
		}
	}
}
