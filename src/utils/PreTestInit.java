package utils;

import model.Movie;
import service.MovieService;

import java.util.HashMap;

import static model.MovieType.*;

public class PreTestInit {
    static MovieService movieService = MovieService.getInstance();

    public static void initMovies(){
        HashMap<String, Movie> movies = new HashMap<>();
        movies.put("F001", new Movie("You've Got Mail", REGULAR.getValue()));
        movies.put("F002", new Movie("Matrix", REGULAR.getValue()));
        movies.put("F003", new Movie("Cars", CHILDRENS.getValue()));
        movies.put("F004", new Movie("Fast & Furious X", NEW.getValue()));
        movieService.addMovies(movies);
    }

}
