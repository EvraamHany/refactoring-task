package service;

import model.Customer;
import model.Movie;
import model.MovieRental;
import java.util.HashMap;
import java.util.Objects;

import static model.MovieType.*;


public class MovieRentalService {

  private static final int INITIAL_REGULAR_MOVIE_ABOUT = 2;
  private static final int MAX_REGULAR_RENTAL_PERIOD = 2;
  private static final double EXTRA_AMOUNT_PER_EACH_EXCTRA_REGULAR_MOVIE_DAY = 1.5;

  private static final double INETIAL_CHILDEREN_MOVIE_AMOUT = 1.5;
  private static final int MAX_CHILDEREN_RENTAL_PERIOD = 3;
  private static final double EXTRA_AMOUNT_PER_EACH_EXCTRA_CHILDEREN_MOVIE_DAY = 1.5;

  private static final double AMOUNT_PER_DAY_FOR_NEW_MOVIE = 3;

  MovieService movieService = MovieService.getInstance();


  public String getRentalStatement(Customer customer) {
    double totalAmount = 0;
    int frequentEnterPoints = 0;
    StringBuilder result = new StringBuilder("Rental Record for " + customer.getName() + "\n");

    for (MovieRental movieRental : customer.getRentals()) {
      double thisAmount = calculateRentalAmount(movieRental);
      frequentEnterPoints++;

      if (isBonusApplicable(movieRental)) {
        frequentEnterPoints++;
      }

      result.append("\t").append(movieService.getMovies().get(movieRental.getMovieId()).getTitle()).append("\t").append(thisAmount).append("\n");
      totalAmount += thisAmount;
    }

    result.append("Amount owed is ").append(totalAmount).append("\n");
    result.append("You earned ").append(frequentEnterPoints).append(" frequent points\n");

    return result.toString();
  }

  private double calculateRentalAmount(MovieRental movieRental) {
    double thisAmount = 0;
    String code = movieService.getMovies().get(movieRental.getMovieId()).getCode();

      if (Objects.equals(code, REGULAR.getValue())) {
        thisAmount = INITIAL_REGULAR_MOVIE_ABOUT;
        if (movieRental.getDays() > MAX_REGULAR_RENTAL_PERIOD) {
          thisAmount += (movieRental.getDays() - MAX_REGULAR_RENTAL_PERIOD) * EXTRA_AMOUNT_PER_EACH_EXCTRA_REGULAR_MOVIE_DAY;
        }
      }

    if (Objects.equals(code, NEW.getValue())) {
      thisAmount = movieRental.getDays() * AMOUNT_PER_DAY_FOR_NEW_MOVIE;
    }

    if (Objects.equals(code, CHILDRENS.getValue())) {
      thisAmount = INETIAL_CHILDEREN_MOVIE_AMOUT;
        if (movieRental.getDays() > MAX_CHILDEREN_RENTAL_PERIOD) {
          thisAmount += (movieRental.getDays() - MAX_CHILDEREN_RENTAL_PERIOD) * EXTRA_AMOUNT_PER_EACH_EXCTRA_CHILDEREN_MOVIE_DAY;
        }
    }

    return thisAmount;
  }

  private boolean isBonusApplicable(MovieRental movieRental) {
    return movieService.getMovies().get(movieRental.getMovieId()).getCode().equals(NEW.getValue()) && movieRental.getDays() > 2;
  }
}
