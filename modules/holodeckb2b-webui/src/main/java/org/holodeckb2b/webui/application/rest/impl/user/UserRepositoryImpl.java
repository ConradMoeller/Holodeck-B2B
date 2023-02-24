package org.holodeckb2b.webui.application.rest.impl.user;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.holodeckb2b.webui.application.Settings;
import org.holodeckb2b.webui.application.rest.api.users.User;
import org.holodeckb2b.webui.application.rest.api.users.UserRepository;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class UserRepositoryImpl implements UserRepository {

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<User> findById(String id) {
		String json;
		try {
			json = new String(Files.readAllBytes(Paths.get(Settings.getDataDir() + "/user/" + id)));
			UserImpl user = new Gson().fromJson(json, UserImpl.class);
			return Optional.of(user);
		} catch (IOException e) {
			return Optional.empty();
		}
	}

	@Override
	public void delete(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<User> save(User user) {
		String json = new Gson().toJson(user);
		File path = new File(Settings.getDataDir() + "/user");
		if (!path.exists()) {
			path.mkdirs();
		}
		try {
			Files.write(Paths.get(Settings.getDataDir() + "/user" + user.getName()), json.getBytes());
			return Optional.of(user);
		} catch (IOException e) {
			return Optional.empty();
		}
	}

}
