package org.holodeckb2b.webui.application.rest.api.users;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

	List<User> findAll();

	Optional<User> findById(String id);

	void delete(User user);

	Optional<User> save(User user);

}
