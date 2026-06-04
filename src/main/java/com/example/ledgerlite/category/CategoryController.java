package com.example.ledgerlite.category;

import com.example.ledgerlite.category.CategoryDtos.CategoryResponse;
import com.example.ledgerlite.category.CategoryDtos.CreateCategoryRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
class CategoryController {
	private final CategoryRepository categories;

	CategoryController(CategoryRepository categories) {
		this.categories = categories;
	}

	@PostMapping
	CategoryResponse create(@Valid @RequestBody CreateCategoryRequest request) {
		return CategoryResponse.from(categories.save(new Category(request.name())));
	}

	@GetMapping
	List<CategoryResponse> list() {
		return categories.findAll().stream().map(CategoryResponse::from).toList();
	}
}
