package service;

import model.Customer;
import model.MovieRental;

import java.util.Objects;

import static model.MovieType.*;
import static utils.Constants.*;


public class MovieRentalService {


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
            thisAmount = getAmountBasedOnInitialMaxAndExtraCharge(movieRental,
                    INITIAL_REGULAR_MOVIE_ABOUT,
                    MAX_REGULAR_RENTAL_PERIOD,
                    EXTRA_AMOUNT_PER_EACH_EXCTRA_REGULAR_MOVIE_DAY);
        }

        if (Objects.equals(code, NEW.getValue())) {
            thisAmount = movieRental.getDays() * AMOUNT_PER_DAY_FOR_NEW_MOVIE;
        }

        if (Objects.equals(code, CHILDRENS.getValue())) {
            thisAmount = getAmountBasedOnInitialMaxAndExtraCharge(movieRental,
                    INETIAL_CHILDEREN_MOVIE_AMOUT,
                    MAX_CHILDEREN_RENTAL_PERIOD,
                    EXTRA_AMOUNT_PER_EACH_EXCTRA_CHILDEREN_MOVIE_DAY);
        }

        return thisAmount;
    }

    private static double getAmountBasedOnInitialMaxAndExtraCharge(MovieRental movieRental,
                                                                   double initialMovieTypeAmount,
                                                                   double maxMovieTypeRentalPeriod,
                                                                   double extraAmountPerExtraDay) {
        double thisAmount;
        thisAmount = initialMovieTypeAmount;
        if (movieRental.getDays() > maxMovieTypeRentalPeriod) {
            thisAmount += (movieRental.getDays() - maxMovieTypeRentalPeriod) * extraAmountPerExtraDay;
        }
        return thisAmount;
    }

    private boolean isBonusApplicable(MovieRental movieRental) {
        return movieService.getMovies().get(movieRental.getMovieId()).getCode().equals(NEW.getValue()) && movieRental.getDays() > 2;
    }
}
