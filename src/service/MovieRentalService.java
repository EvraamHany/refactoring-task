package service;

import model.Customer;
import model.MovieRental;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static model.MovieType.*;
import static utils.Constants.*;


public class MovieRentalService {


    private MovieService movieService = MovieService.getInstance();


    public String getRentalStatement(Customer customer) {
        StringBuilder result = new StringBuilder("Rental Record for " + customer.getName() + "\n");

        double totalAmount = customer.getRentals()
                .stream()
                .mapToDouble(movieRental -> {
                    try {
                        return calculateRentalAmount(movieRental);
                    } catch (Exception e) {
                        throw new RuntimeException("error calculating total amount");
                    }
                })
                .sum();

        int frequentEnterPoints = customer.getRentals()
                .stream()
                .mapToInt(movieRental -> {
                    try {
                        return isBonusApplicable(movieRental) ? 2 : 1;
                    } catch (Exception e) {
                        throw new RuntimeException("error calculating frequent Enter Points");
                    }
                })
                .sum();

        String rentalDetails = customer.getRentals()
                .stream()
                .map(movieRental -> {
                    try {
                        String title = movieService.getMovies().get(movieRental.getMovieId()).getTitle();
                        double amount = calculateRentalAmount(movieRental);
                        return "\t" + title + "\t" + amount + "\n";
                    } catch (Exception e) {
                        throw new RuntimeException("error creating rental details");
                    }
                })
                .collect(Collectors.joining());

        result.append(rentalDetails);
        result.append("Amount owed is ").append(totalAmount).append("\n");
        result.append("You earned ").append(frequentEnterPoints).append(" frequent points\n");

        return result.toString();
    }

    private double calculateRentalAmount(MovieRental movieRental) {
        String code = movieService.getMovies().get(movieRental.getMovieId()).getCode();
        Map<String, Function<MovieRental, Double>> calculationStrategies = Map.of(
                REGULAR.getValue(), this::calculateRegularMoviesRentalAmount,
                NEW.getValue(), this::calculateNewMoviesRentalAmount,
                CHILDRENS.getValue(), this::calculateChildrenMoviesRentalAmount
        );
        return calculationStrategies.getOrDefault(code, rental -> 0.0).apply(movieRental);
    }

    private double calculateRegularMoviesRentalAmount(MovieRental movieRental) {
        return getAmountBasedOnInitialMaxAndExtraCharge(movieRental,
                INITIAL_REGULAR_MOVIE_ABOUT,
                MAX_REGULAR_RENTAL_PERIOD,
                EXTRA_AMOUNT_PER_EACH_EXCTRA_REGULAR_MOVIE_DAY);
    }

    private double calculateNewMoviesRentalAmount(MovieRental movieRental) {
        return movieRental.getDays() * AMOUNT_PER_DAY_FOR_NEW_MOVIE;
    }

    private double calculateChildrenMoviesRentalAmount(MovieRental movieRental) {
        return getAmountBasedOnInitialMaxAndExtraCharge(movieRental,
                INETIAL_CHILDEREN_MOVIE_AMOUT,
                MAX_CHILDEREN_RENTAL_PERIOD,
                EXTRA_AMOUNT_PER_EACH_EXCTRA_CHILDEREN_MOVIE_DAY);
    }

    private static double getAmountBasedOnInitialMaxAndExtraCharge(MovieRental movieRental,
                                                                   double initialMovieAmount,
                                                                   double maxMovieRentalPeriod,
                                                                   double extraAmountPerExtraDay) {
        double thisAmount;
        thisAmount = initialMovieAmount;
        if (movieRental.getDays() > maxMovieRentalPeriod) {
            thisAmount += (movieRental.getDays() - maxMovieRentalPeriod) * extraAmountPerExtraDay;
        }
        return thisAmount;
    }

    private boolean isBonusApplicable(MovieRental movieRental) {
        return movieService.getMovies().get(movieRental.getMovieId()).getCode().equals(NEW.getValue()) &&
                movieRental.getDays() > MAX_NEW_RENTAL_PERIOD;
    }
}
