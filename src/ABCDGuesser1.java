
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.FormatChecker;

/**
 * Use the de Jager formula to calculate the exponents.
 *
 * @author Zheyuan Gao
 *
 */

public final class ABCDGuesser1 {

    private ABCDGuesser1() {
        //Private constructor so this utility class cannot be instantiated.
    }

    /**
     * Repeatedly asks the user for a positive real number until the user enters
     * one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number entered by the user
     */
    private static double getPositiveDouble(SimpleReader in, SimpleWriter out) {
        //use a boolean variable to determine if the user's input satisfies the
        //requirement
        boolean done = false;
        String userInput;
        double userDouble = 0;
        while (!done) {
            out.print("please enter a positive real number for estimation: ");
            userInput = in.nextLine();
            //first check if the user input an double
            if (FormatChecker.canParseDouble(userInput)) {
                userDouble = Double.parseDouble(userInput);
                //then check if the double is positive
                if (userDouble > 0) {
                    //the user input satisfies all requirements, turn the boolean
                    //to true
                    done = true;
                }
            }
        }
        /*
         * return the double to the mean method
         */
        return userDouble;

    }

    /**
     * Repeatedly asks the user for a positive real number not equal to 1.0
     * until the user enters one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number not equal to 1.0 entered by the user
     */
    private static double getPositiveDoubleNotOne(SimpleReader in,
            SimpleWriter out) {
        //use a boolean variable to determine if the user's input satisfies the
        //requirement
        boolean done = false;
        String userInput;
        double userDouble = 0;
        while (!done) {
            out.print(
                    "please enter a random positive real number(except 1.0) for "
                            + "estimation: ");
            userInput = in.nextLine();
            //first check if the user input an double
            if (FormatChecker.canParseDouble(userInput)) {
                userDouble = Double.parseDouble(userInput);
                //then check if the double is positive
                if (userDouble > 0 && userDouble != 1) {
                    //the user input satisfies all requirements, turn the boolean
                    //to true
                    done = true;
                }
            }
        }
        /*
         * return the double to the mean method
         */
        return userDouble;

    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */

    public static void main(String[] args) {
        /*
         * A list of integer to prevent the appearance of magic numbers
         */
        final int one = 1;
        final int two = 2;
        final int three = 3;
        final int four = 4;
        final int five = 5;
        final int hundred = 100;
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        //finalNum is the goal of the estimation
        double finalNum = getPositiveDouble(in, out);
        //userNum is an array stores w, x, y, z from the user
        double[] userNum = new double[four];
        int i = 0;
        while (i < userNum.length) {
            userNum[i] = getPositiveDoubleNotOne(in, out);
            i++;
        }

        //create the array to store the exponents
        double[] exponents = new double[four];
        //the array to store candidates of the exponents
        double[] possibleExponents = { -five, -four, -three, -two, -one,
                -1.0 / two, -1.0 / three, -1.0 / four, 0, 1.0 / four,
                1.0 / three, 1.0 / two, 1, 2, three, four, five };
        //an array has four zero that controls how many times the loop will
        //execute
        int[] counts = { 0, 0, 0, 0 };
        //Declare a double variable to store the value of each estimate
        double estimate;
        /*
         * Declare a double variable named error and assign the value to the
         * initial error between the finalNum and the userNum with exponents all
         * equal to -5
         */
        double error = Math.pow(userNum[0], possibleExponents[counts[0]])
                * Math.pow(userNum[1], possibleExponents[counts[1]])
                * Math.pow(userNum[2], possibleExponents[counts[2]])
                * Math.pow(userNum[three], possibleExponents[counts[three]])
                - finalNum;

        /*
         * counts[3] means the 17 possibilities for the exponents of the forth
         * user input number
         */
        while (counts[three] < possibleExponents.length) {
            /*
             * counts[2] means the 17 possibilities for the exponents of the
             * third user input number
             */
            while (counts[2] < possibleExponents.length) {
                /*
                 * counts[1] means the 17 possibilities for the exponents of the
                 * second user input number
                 */
                while (counts[1] < possibleExponents.length) {
                    /*
                     * counts[3] means the 17 possibilities for the exponents of
                     * the first user input number
                     */
                    while (counts[0] < possibleExponents.length) {
                        /*
                         * the estimate means the product of the user numbers in
                         * this specific exponents situation
                         */
                        estimate = Math.pow(userNum[0],
                                possibleExponents[counts[0]])
                                * Math.pow(userNum[1],
                                        possibleExponents[counts[1]])
                                * Math.pow(userNum[2],
                                        possibleExponents[counts[2]])
                                * Math.pow(userNum[three],
                                        possibleExponents[counts[three]]);
                        /*
                         * As long as the error this time is less than last
                         * time, the program will update the error and exponents
                         * to the latest situation.
                         */
                        if (Math.abs(estimate - finalNum) < Math.abs(error)) {
                            error = Math.abs(estimate - finalNum);
                            exponents[0] = possibleExponents[counts[0]];
                            exponents[1] = possibleExponents[counts[1]];
                            exponents[2] = possibleExponents[counts[2]];
                            exponents[three] = possibleExponents[counts[three]];
                        }
                        //change the choice of the exponent for first user input number
                        counts[0]++;
                    }
                    //change the choice of the exponent for second user input number
                    counts[1]++;
                    //reset the exponent of the first user input number back to 0
                    counts[0] = 0;
                }
                //change the choice of the exponent for third user input number
                counts[2]++;
                //reset the exponent of the second user input number back to 0
                counts[1] = 0;
            }
            //change the choice of the exponent for fourth user input number
            counts[three]++;
            //reset the exponent of the third user input number back to 0
            counts[2] = 0;
        }

        //output put the value of a, b, c, and, d to the screen
        out.println("a: " + exponents[0]);
        out.println("b: " + exponents[1]);
        out.println("c: " + exponents[2]);
        out.println("d: " + exponents[three]);
        //output the result of estimation
        double result = Math.pow(userNum[0], exponents[0])
                * Math.pow(userNum[1], exponents[1])
                * Math.pow(userNum[2], exponents[2])
                * Math.pow(userNum[three], exponents[three]);
        out.print("The estimated result: ");
        out.println(result, 2, false);
        //output the relative error in percentage
        error = Math.abs(result - finalNum) / finalNum * hundred;
        out.print("The relative error: ");
        out.print(error, 2, false);
        out.println(" %");

        /*
         * Close input and output streams
         */
        in.close();
        out.close();

    }

}
