package edu.kirkwood.view;

import static edu.kirkwood.view.UIUtility.displayMessage;

public class Messages {

    /**
     * Displays the application's welcome message.
     */
    public static void hello() {
        displayMessage("Welcome to the Kirkwood Calculators Application");
    }

    /**
     * Displays the application's farewell message.
     */
    public static void goodbye() {
        displayMessage("Goodbye");
    }

    /**
     * Displays instructions for using the fraction calculator.
     */
    public static void fractionGreet() {
        displayMessage("Welcome to Marc's Fraction Calculator");
        System.out.println("Enter calculations in the format: [fraction] [operator] [fraction]");
        System.out.println("Example: 1 1/2 + 3/4\n");
    }

    /**
     * Displays the fraction calculator's farewell message.
     */
    public static void fractionGoodbye() {
        displayMessage("Thank you for using Marc's Fraction Calculator");
    }

    /**
     * Displays the Half Life calculator's greeting message.
     */
    public static void halfLifeHello() {
        System.out.println();
        displayMessage("Welcome to Xavian's Half-Life Calculator!");
        System.out.println("Select an option below to start:");
    }

    /**
     * Displays the half life calculator's farewell message.
     */
    public static void halfLifeGoodbye() {
        System.out.println();
        displayMessage("Thank you for using Xavian's Half-Life Calculator.");
    }
}
