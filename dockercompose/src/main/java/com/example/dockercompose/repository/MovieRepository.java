package com.example.dockercompose.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dockercompose.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
	List<Movie> findByBlockbuster(boolean blockbuster);
	List<Movie> findByTitleContaining(String title);
}
