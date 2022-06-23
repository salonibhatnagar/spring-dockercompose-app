package com.example.dockercompose.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dockercompose.model.Movie;
import com.example.dockercompose.repository.MovieRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class MovieController {

	@Autowired
	MovieRepository movieRepository;

	@GetMapping("/movies")
	public ResponseEntity<List<Movie>> getAllMovies(@RequestParam(required = false) String title) {
		try {
			List<Movie> movieList = new ArrayList<Movie>();

			if (title == null)
				movieRepository.findAll().forEach(movieList::add);
			else
				movieRepository.findByTitleContaining(title).forEach(movieList::add);

			if (movieList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(movieList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/movies/{id}")
	public ResponseEntity<Movie> getMovieById(@PathVariable("id") long id) {
		Optional<Movie> movieData = movieRepository.findById(id);

		if (movieData.isPresent()) {
			return new ResponseEntity<>(movieData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/movies")
	public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
		try {
			Movie mv = movieRepository
					.save(new Movie(movie.getTitle(), movie.getDescription(), false));
			return new ResponseEntity<>(mv, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/movies/{id}")
	public ResponseEntity<Movie> updateMovie(@PathVariable("id") long id, @RequestBody Movie movie) {
		Optional<Movie> movieData = movieRepository.findById(id);

		if (movieData.isPresent()) {
			Movie mv = movieData.get();
			mv.setTitle(movie.getTitle());
			mv.setDescription(movie.getDescription());
			mv.setBlockbuster(movie.isBlockbuster());
			return new ResponseEntity<>(movieRepository.save(mv), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/movies/{id}")
	public ResponseEntity<HttpStatus> deleteMovie(@PathVariable("id") long id) {
		try {
			movieRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/movies")
	public ResponseEntity<HttpStatus> deleteAllMovies() {
		try {
			movieRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/movies/blockbuster")
	public ResponseEntity<List<Movie>> findByBlockbuster() {
		try {
			List<Movie> movies = movieRepository.findByBlockbuster(true);

			if (movies.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(movies, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
