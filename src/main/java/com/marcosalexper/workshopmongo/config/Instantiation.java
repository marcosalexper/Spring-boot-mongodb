package com.marcosalexper.workshopmongo.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.marcosalexper.workshopmongo.domain.Post;
import com.marcosalexper.workshopmongo.domain.User;
import com.marcosalexper.workshopmongo.dto.AuthorDTO;
import com.marcosalexper.workshopmongo.dto.CommentDTO;
import com.marcosalexper.workshopmongo.repository.PostRepository;
import com.marcosalexper.workshopmongo.repository.UserRepository;

@Configuration
public class Instantiation  implements CommandLineRunner{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		userRepository.deleteAll();
		postRepository.deleteAll();
		
		
		User maria = new User(null, "Maria Brown", "maria@gmail.com"); 
		User alex = new User(null, "Alex Green", "alex@gmail.com"); 
		User bob = new User(null, "Bob Grey", "bob@gmail.com");
		
		userRepository.saveAll(Arrays.asList(maria, alex, bob));
		
		Post post1 = new Post(null, sdf.parse("15/10/2024"), "Partiu viagem!", "Vou viajar para o nordeste, enfim férias!", new AuthorDTO(maria));
		Post post2 = new Post(null, sdf.parse("14/10/2024"), "Bom dia!", "Está um belo dia hoje!", new AuthorDTO(maria));
		
		CommentDTO c1 = new CommentDTO("Boa viagem!!", sdf.parse("16/10/2024"), new AuthorDTO(alex));
		CommentDTO c2 = new CommentDTO("Aproveite!", sdf.parse("16/10/2024"), new AuthorDTO(bob));
		CommentDTO c3 = new CommentDTO("Um ótimo dia para nós!", sdf.parse("16/10/2024"), new AuthorDTO(alex));
		
		post1.getComments().addAll(Arrays.asList(c1, c2));
		post2.getComments().addAll(Arrays.asList(c3));
			
		postRepository.saveAll(Arrays.asList(post1, post2));
		
		maria.getPosts().addAll(Arrays.asList(post1, post2));
		userRepository.save(maria);
	}

}
