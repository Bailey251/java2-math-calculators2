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
     * Displays the retirement calculator welcome message.
     */
    public static void retirementGreet() {
        displayMessage("Welcome to the Retirement Calculator");
        System.out.println("Plan for a secure future by estimating your retirement balance.");
    }

    /**
     * Displays retirement plan guidance and key terms.
     */
    public static void retirementTerms() {
        System.out.println("401(k): A retirement savings account sponsored by an employer.");
        System.out.println("Contribution rate: The percentage of your salary you contribute each year.");
        System.out.println("Employer match: The percentage of your salary the employer matches.");
        System.out.println("Return rate: The expected annual investment growth rate.");
    }

    /**
     * Displays the retirement calculator farewell message.
     */
    public static void retirementGoodbye() {
        displayMessage("Thank you for using the Retirement Calculator");
    }
}
