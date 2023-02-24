package org.holodeckb2b.webui.application.rest.api.users.v1;

import java.util.Optional;

import org.holodeckb2b.webui.application.TOTPTool;
import org.holodeckb2b.webui.application.rest.api.users.User;
import org.holodeckb2b.webui.application.rest.api.users.UserRepository;
import org.holodeckb2b.webui.application.rest.impl.user.UserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	private final UserRepository repository;

	@Autowired
	public UserController(UserRepository repository) {
		this.repository = repository;
	}

	@PostMapping
	public String createUser(@RequestParam(name = "username") String username) {
		User user = new UserImpl();
		user.setId(username.toLowerCase().replace(" ", "_"));
		user.setName(username);
		user.setSecret(TOTPTool.generateSecretKey());
		Optional<User> save = repository.save(user);
		if (save.isPresent()) {
			return save.get().getSecret();
		} else {
			return "error";
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable String id) {
		Optional<User> user = repository.findById(id);
		if (!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		repository.delete(user.get());
		return ResponseEntity.noContent().build();
	}
}
