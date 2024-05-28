import model.Customer;
import model.Movie;
import model.MovieRental;
import service.MovieRentalService;
import service.MovieService;

import java.util.Arrays;
import java.util.HashMap;

import static model.MovieType.*;

public final class Main {

    public static void main(String[] args) {
        MovieService movieService = MovieService.getInstance();
        MovieRentalService movieRentalService=new MovieRentalService();

        //given
        HashMap<String, Movie> movies = new HashMap<>();
        movies.put("F001", new Movie("You've Got Mail", REGULAR.getValue()));
        movies.put("F002", new Movie("Matrix", REGULAR.getValue()));
        movies.put("F003", new Movie("Cars", CHILDRENS.getValue()));
        movies.put("F004", new Movie("Fast & Furious X", NEW.getValue()));
        movieService.addMovies(movies);

        //When
        String result = movieRentalService.getRentalStatement(new Customer("C. U. Stomer", Arrays.asList(new MovieRental("F001", 3), new MovieRental("F002", 1))));
        //String result = movieRentalService.statement(new Customer("C. U. Stomer", Arrays.asList(new MovieRental("F001", 3), new MovieRental("F002", 1), new MovieRental("F004", 5))));

        //Then
        String expected = "Rental Record for C. U. Stomer\n\tYou've Got Mail\t3.5\n\tMatrix\t2.0\nAmount owed is 5.5\nYou earned 2 frequent points\n";
        if (!result.equals(expected)) {
            throw new AssertionError("Expected: " + System.lineSeparator() + String.format(expected) + System.lineSeparator() + System.lineSeparator() + "Got: " + System.lineSeparator() + result);
        }
        System.out.println("Success");
    }
}
