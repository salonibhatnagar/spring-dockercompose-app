package com.example.dockercompose.model;

import javax.persistence.*;

@Entity
@Table(name = "movies")
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name = "blockbuster")
	private boolean blockbuster;

	public Movie() {

	}

	public Movie(String title, String description, boolean blockbuster) {
		this.title = title;
		this.description = description;
		this.blockbuster = blockbuster;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isBlockbuster() {
		return blockbuster;
	}

	public void setBlockbuster(boolean blockbuster) {
		this.blockbuster = blockbuster;
	}

	@java.lang.Override
	public java.lang.String toString() {
		return "Movie{" +
				"id=" + id +
				", title='" + title + '\'' +
				", description='" + description + '\'' +
				", blockbuster=" + blockbuster +
				'}';
	}
}
