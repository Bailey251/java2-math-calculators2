package edu.kirkwood.model.evan;

import edu.kirkwood.view.Helpers;

import java.time.DateTimeException;
import java.time.LocalDate;

/**
 * Represents a calculator other determines the day of the week for a given date
 * using Conway's Doomsday algorithm.
 */
public class DoomsdayCalculator implements Comparable<DoomsdayCalculator>  {

    private LocalDate date;

    /**
     *
     * Javadoc comments written by ChatGPT
     * https://chatgpt.com/c/6aa7cb3d-eed8-83ea-acf5-ae8f442e36ea
     *
     */

    /**
     * Constructs a DoomsdayCalculator with the current date
     */
    public DoomsdayCalculator() {
        date = LocalDate.now();
    }

    /**
     * Constructs a DoomsdayCalculator with the specified date.
     *
     * @param date the date to use
     */
    public DoomsdayCalculator(LocalDate date) {
        this.date = date;
    }

    /**
     * Returns the year of the date.
     *
     * @return the year
     */
    public int getYear() {
        return date.getYear();
    }

    /**
     * Sets the year of the date.
     *
     * @param year the year to set
     * @throws IllegalArgumentException if the value is less than 1
     */
    public void setYear(int year) {
        if (year < 1) {
            throw new IllegalArgumentException("Year cannot be less than 1");
        }
        date = LocalDate.of(year, date.getMonthValue(), date.getDayOfMonth());
    }

    /**
     * Returns the month of the date.
     *
     * @return the month
     */
    public int getMonth() {
        return date.getMonthValue();
    }

    /**
     * Sets the month of the date.
     *
     * @param month the month to set
     * @throws DateTimeException if the resulting date is invalid
     */
    public void setMonth(int month) {
        date = LocalDate.of(date.getYear(), month, date.getDayOfMonth());
    }

    /**
     * Returns the day of the date.
     *
     * @return the day
     */
    public int getDay() {
        return date.getDayOfMonth();
    }

    /**
     * Sets the day of the date.
     *
     * @param day the day to set
     * @throws DateTimeException if the resulting date is invalid
     */
    public void setDay(int day) {
        date = LocalDate.of(date.getYear(), date.getMonthValue(), day);
    }

    /**
     * Calculates the anchor day for the century of the stored year.
     *
     * @return the century anchor day
     */
    public int getCenturyAnchorDay() {
        // Got the math from this link: https://rspk.org/notes/doomsday-rule
        int century = getYear() / 100;
        int remainder = century % 4;

        if (remainder == 0) {
            return 2;
        } else if (remainder == 1) {
            return 0;
        } else if (remainder == 2) {
            return 5;
        } else {
            return 3;
        }
    }

    /**
     * Displays the steps used to calculate the century anchor for the given year.
     *
     * @return the calculation steps as a formatted string
     */

    public String getCenturyAnchorDayCalculationSteps() {
        int century = getYear() / 100;
        int remainder = century % 4;

        int anchorValue;

        if (remainder == 0) {
            anchorValue = 2;
        } else if (remainder == 1) {
            anchorValue = 0;
        } else if (remainder == 2) {
            anchorValue = 5;
        } else {
            anchorValue = 3;
        }

        return String.format(
                """
                    Century anchor calculation steps:
                    Year value: %d
                    1) Divide the year by 100 to find the century: %d / 100 = %d
                    2) Find the remainder when the century is divided by 4: %d %% 4 = %d
                    3) Determine the century anchor based on the remainder:
                          Remainder of 0: anchor = 2
                          Remainder of 1: anchor = 0
                          Remainder of 2: anchor = 5
                          Remainder of 3: anchor = 3
                    4) Century anchor: %d""",
                getYear(),
                getYear(), century,
                century, remainder,
                anchorValue
        );
    }


    /**
     * Calculates the Doomsday for the stored year using the century anchor day
     * and the last two digits of the year.
     *
     * @return the Doomsday for the year
     */

    public int calculateDoomsdayDay() {
        int lastTwoDigits = getYear() % 100;
        int quotient = lastTwoDigits / 12;
        int remainder = lastTwoDigits % 12;
        int remainderQuotient = remainder / 4;

        return (getCenturyAnchorDay() + quotient + remainder + remainderQuotient) % 7;
    }

    /**
     * Displays the steps used to calculate the Doomsday for the given year.
     *
     * @return the calculation steps as a formatted string
     */
    public String getCalculationSteps() {
        int lastTwoDigits = getYear() % 100;
        int quotient = lastTwoDigits / 12;
        int remainder = lastTwoDigits % 12;
        int remainderQuotient = remainder / 4;
        int centuryAnchor = getCenturyAnchorDay();
        int total = centuryAnchor + quotient + remainder + remainderQuotient;
        int doomsday = total % 7;

        return String.format(
                """
                    Doomsday day calculation steps:
                    Century anchor (from prior calculations): %d
                    Year value: %d
                    Last two digits of the year value: %d
                    1) How many times does the number 12 fit as a whole into the two last digits of the year number? 12 fits in %d time(s)
                    2) What is the difference between the two last digits of the year number and the product of the multiples of 12 from calculation 1 (remainder)? %d - %d = %d
                    3) How many times does the number 4 fit into the result of calculation 2? 4 fits in %d time(s)
                    4) Add everything together: %d + %d + %d + %d = %d
                    5) Divide by 7, the numbers added together in step 5: %d %% 7 = %d""",
                centuryAnchor,
                getYear(),
                lastTwoDigits,
                quotient,
                lastTwoDigits, (12 * quotient), remainder,
                remainderQuotient,
                centuryAnchor, quotient, remainder, remainderQuotient, total,
                total, doomsday
        );
    }

    /**
     * Determines the Doomsday date for the month of the stored date, accounting
     * for leap years when necessary.
     *
     * @return the Doomsday date for the month
     */
    public int getMonthDoomsday() {
        switch (getMonth()) {
            case 1:
                if (isLeapYear()) {
                    return 4;
                }
                return 3;
            case 2:
                if (isLeapYear()) {
                    return 29;
                }
                return 28;
            case 3:
                return 14;
            case 4:
                return 4;
            case 5:
                return 9;
            case 6:
                return 6;
            case 7:
                return 11;
            case 8:
                return 8;
            case 9:
                return 5;
            case 10:
                return 10;
            case 11:
                return 7;
            case 12:
                return 12;
            default:
                return -1;
        }
    }

    /**
     * Determines whether the stored year is a leap year.
     *
     * @return true if the year is a leap year; false otherwise
     */
    public boolean isLeapYear() {
        return (getYear() % 4 == 0 && getYear() % 100 != 0) || getYear() % 400 == 0;
    }

    /**
     * Calculates the day of the week for the stored date using Conway's
     * Doomsday algorithm.
     *
     * @return the weekday and date as a formatted string
     */
    public String calculateDateWeekday() {
        int monthDoomsday = getMonthDoomsday();
        int doomsday = calculateDoomsdayDay();

        int difference = getDay() - monthDoomsday;
        int weekday = (doomsday + difference) % 7;

        if (weekday < 0) {
            weekday += 7;
        }

        String[] weekdays = {
                "Sunday", "Monday", "Tuesday", "Wednesday",
                "Thursday", "Friday", "Saturday"
        };

        return weekdays[weekday] + ", " + this;
    }

    /**
     * Returns the date as a string in long date format.
     *
     * @return the formatted date
     */
    @Override
    public String toString() {
        return Helpers.formatDateLong(date);
    }

    /**
     * Compares this DoomsdayCalculator to another DoomsdayCalculator
     * based on their dates.
     *
     * @param o the DoomsdayCalculator to compare to
     * @return a negative value if this date is earlier, zero if the dates
     *         are equal, or a positive value if this date is later
     */
    @Override
    public int compareTo(DoomsdayCalculator o) {
        if (getYear() != o.getYear()) {
            return getYear() - o.getYear();
        }

        if (getMonth() != o.getMonth()) {
            return getMonth() - o.getMonth();
        }

        return getDay() - o.getDay();
    }

    /**
     * Determines whether this DoomsdayCalculator is equal to another object
     * based on their dates.
     *
     * @param o the object to compare to
     * @return true if the objects have the same date; false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DoomsdayCalculator other = (DoomsdayCalculator) o;

        return getYear() == other.getYear() && getMonth() == other.getMonth() && getDay() == other.getDay();
    }
}