package com.marcosalexper.workshopmongo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcosalexper.workshopmongo.domain.User;
import com.marcosalexper.workshopmongo.dto.UserDTO;
import com.marcosalexper.workshopmongo.repository.UserRepository;
import com.marcosalexper.workshopmongo.services.exception.ObjectNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository repo;

	public List<User> findAll() {
		return repo.findAll();
	}

	public User findBydId(String id) {
		Optional<User> user = repo.findById(id);
		return user.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!"));
	}

	public User insert(User obj) {
		return repo.insert(obj);
	}

	public void deleteById(String id) {
		findBydId(id);
		repo.deleteById(id);
	}

	public User update(User obj) {
		User newObj = findBydId(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	private void updateData(User newObj, User obj) {
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());
	}

	public User fromDTO(UserDTO objDto) {
		return new User(objDto.getId(), objDto.getName(), objDto.getEmail());
	}

}
