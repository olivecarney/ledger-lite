package com.example.ledgerlite.user;

import com.example.ledgerlite.common.NotFoundException;
import com.example.ledgerlite.user.UserDtos.CreateUserRequest;
import com.example.ledgerlite.user.UserDtos.UserResponse;
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
@RequestMapping("/users")
class UserController {
	private final UserRepository users;

	UserController(UserRepository users) {
		this.users = users;
	}

	@PostMapping
	UserResponse create(@Valid @RequestBody CreateUserRequest request) {
		return UserResponse.from(users.save(new AppUser(request.name(), request.email())));
	}

	@GetMapping
	List<UserResponse> list() {
		return users.findAll().stream().map(UserResponse::from).toList();
	}

	@GetMapping("/{id}")
	UserResponse get(@PathVariable UUID id) {
		return users.findById(id)
				.map(UserResponse::from)
				.orElseThrow(() -> new NotFoundException("User not found: " + id));
	}
}
