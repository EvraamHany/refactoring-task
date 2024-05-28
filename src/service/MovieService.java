package service;

import model.Movie;

import java.util.HashMap;


public class MovieService {
private static MovieService instance;
  private  HashMap<String, Movie> movies = new HashMap<>();

  private MovieService() {
  }
  public static MovieService getInstance() {
    if (instance == null) {
      synchronized (MovieService.class) {
        if (instance == null) {
          instance = new MovieService();
        }
      }
    }
    return instance;
  }
  public void addMovies(HashMap<String, Movie> movies) {
    this.movies = movies;
  }

  public HashMap<String, Movie> getMovies() {
    return movies;
  }
}
