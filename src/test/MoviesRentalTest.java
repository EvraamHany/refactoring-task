package test;

import model.Customer;
import model.MovieRental;
import service.MovieRentalService;
import utils.PreTestInit;

import java.util.Arrays;

public class MoviesRentalTest {

    public static void testCustomerRentalStatementSuccessScenario(){

        //given
        PreTestInit.initMovies();

        //when
        MovieRentalService movieRentalService = new MovieRentalService();
        String result = movieRentalService.getRentalStatement(new Customer("C. U. Stomer", Arrays.asList(new MovieRental("F001", 3), new MovieRental("F002", 1))));

        //Then
        String expected = "Rental Record for C. U. Stomer\n\tYou've Got Mail\t3.5\n\tMatrix\t2.0\nAmount owed is 5.5\nYou earned 2 frequent points\n";
        if (!result.equals(expected)) {
            throw new AssertionError("Expected: " + System.lineSeparator() + String.format(expected) + System.lineSeparator() + System.lineSeparator() + "Got: " + System.lineSeparator() + result);
        }
        System.out.println("Success");
    }

    public static void testCustomerRentalStatementSuccessScenarioWhereTypeIsNew(){

        //given
        PreTestInit.initMovies();

        //when
        MovieRentalService movieRentalService = new MovieRentalService();
        String result = movieRentalService.getRentalStatement(new Customer("C. U. Stomer", Arrays.asList(new MovieRental("F001", 3), new MovieRental("F002", 1), new MovieRental("F004", 5))));

        //Then
        String expected = "Rental Record for C. U. Stomer\n\tYou've Got Mail\t3.5\n\tMatrix\t2.0\n\tFast & Furious X\t15.0\nAmount owed is 20.5\nYou earned 4 frequent points\n";
        if (!result.equals(expected)) {
            throw new AssertionError("Expected: " + System.lineSeparator() + String.format(expected) + System.lineSeparator() + System.lineSeparator() + "Got: " + System.lineSeparator() + result);
        }
        System.out.println("Success");
    }

}
