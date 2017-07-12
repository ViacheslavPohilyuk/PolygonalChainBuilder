/********************************************************************************************
 *
 * Compilation: javac Main.java Dot.java
 * Execution: java Main {Count of dotes}
 *            or java Main and enter a value after the program is started
 * Appointment of the project: Creating a closed polygonal chain with
 *                             an arbitrary set of dotes with random coordinates
 *
 *********************************************************************************************
 */

import java.util.*;

public class Main {
    /**
     * In the method {@code main}:
     * Firstly, we put value of count of dotes. And do it either using
     * command-line or through {@code System.in}.
     * Secondly, we put value of count of dotes to the method {@code dotesGenerator} and
     * it generates an array of dotes.
     * Thirdly, displacing all dotes relatively of the center of coordinate system using method
     * {@code offsetToCenter} and sorting by angle using method {@code sortByAngle}
     * Finally, output a sequence of dotes to build polygonal chain and creating
     * the image with closed polygonal chain on the cartesian coordinate system
     * using method {@code buildPolygonalChain}
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        Dot[] dotes = new Dot[0];
        try {
            dotes = Dot.dotesGenerator(Integer.parseInt(args[0]));
        } catch (NumberFormatException e) {
            System.out.println("Wrong input value or the one too big!");
            System.out.println("You can use only digits in the argument.");
        } catch (ArrayIndexOutOfBoundsException e) {
            boolean rightInput = false;
            do {
                try {
                    System.out.println("Enter an integer number:");
                    Scanner sc = new Scanner(System.in);

                    int dotesCount = sc.nextInt();
                    if (dotesCount <= 0) {
                        System.out.println("Count of dotes can't be zero or negative number. Try one more.");
                    } else {
                        dotes = Dot.dotesGenerator(dotesCount);
                        System.out.println();
                        rightInput = true;
                    }
                } catch (InputMismatchException ex) {
                    System.err.println("Only integer numbers are accepted! Try one more.");
                }
            }
            while (!rightInput);
        }

        dotes = Dot.offsetToCenter(dotes);
        System.out.println(Dot.sortByAngle(dotes));

        // Building a figure
        System.out.println("Figure of closed polygonal chain:");
        Dot.buildPolygonalChain(dotes);
    }
}
