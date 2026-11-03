package edu.kirkwood.model.evan;

import edu.kirkwood.view.Helpers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DateTimeException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DoomsdayCalculatorTest {

    private DoomsdayCalculator dc1;
    private DoomsdayCalculator dc2;
    private LocalDate today;

    @BeforeEach
    void setUp() {
        dc1 = new DoomsdayCalculator();
        dc2 = new DoomsdayCalculator(LocalDate.of(1, 1, 1));
        today = LocalDate.now();
    }

    @Test
    void testToString() {
        assertEquals(Helpers.formatDateLong(today), dc1.toString());
        assertEquals("January 1, 1", dc2.toString());
    }

    @Test
    void getYear() {
        assertEquals(today.getYear(), dc1.getYear());
        assertEquals(1, dc2.getYear());
    }

    @Test
    void setYear() {
        dc1.setYear(2024);
        dc2.setYear(2000);

        assertEquals(2024, dc1.getYear());
        assertEquals(2000, dc2.getYear());
    }

    @Test
    void setYearInvalid() {
        assertThrows(IllegalArgumentException.class, () -> dc1.setYear(0));
        assertThrows(IllegalArgumentException.class, () -> dc2.setYear(-1));
    }

    @Test
    void getMonth() {
        assertEquals(today.getMonthValue(), dc1.getMonth());
        assertEquals(1, dc2.getMonth());
    }

    @Test
    void setMonth() {
        dc1.setMonth(1);
        dc2.setMonth(12);

        assertEquals(1, dc1.getMonth());
        assertEquals(12, dc2.getMonth());
    }

    @Test
    void setMonthInvalid() {
        assertThrows(DateTimeException.class, () -> dc1.setMonth(0));
        assertThrows(DateTimeException.class, () -> dc2.setMonth(13));
    }

    @Test
    void getDay() {
        assertEquals(today.getDayOfMonth(), dc1.getDay());
        assertEquals(1, dc2.getDay());
    }

    @Test
    void setDay() {
        dc1.setDay(1);
        dc2.setDay(31);

        assertEquals(1, dc1.getDay());
        assertEquals(31, dc2.getDay());
    }

    @Test
    void setDayInvalid() {
        assertThrows(DateTimeException.class, () -> dc1.setDay(0));
        assertThrows(DateTimeException.class, () -> dc2.setDay(32));
    }

    @Test
    void isLeapYear() {
        dc1.setYear(2024);
        dc2.setYear(2000);

        assertTrue(dc1.isLeapYear());
        assertTrue(dc2.isLeapYear());
    }

    @Test
    void isNotLeapYear() {
        dc1.setYear(2025);
        dc2.setYear(1900);

        assertFalse(dc1.isLeapYear());
        assertFalse(dc2.isLeapYear());
    }

    @Test
    void getCenturyAnchorDay() {
        dc1.setYear(1800);
        assertEquals(5, dc1.getCenturyAnchorDay());

        dc1.setYear(1900);
        assertEquals(3, dc1.getCenturyAnchorDay());

        dc1.setYear(2000);
        assertEquals(2, dc1.getCenturyAnchorDay());

        dc1.setYear(2100);
        assertEquals(0, dc1.getCenturyAnchorDay());
    }

    @Test
    void getCenturyAnchorDayCalculationStepsWithRemainderZero() {
        DoomsdayCalculator dc = new DoomsdayCalculator(LocalDate.of(2000, 1, 1));

        String expected =
                """
                    Century anchor calculation steps:
                    Year value: 2000
                    1) Divide the year by 100 to find the century: 2000 / 100 = 20
                    2) Find the remainder when the century is divided by 4: 20 % 4 = 0
                    3) Determine the century anchor based on the remainder:
                          Remainder of 0: anchor = 2
                          Remainder of 1: anchor = 0
                          Remainder of 2: anchor = 5
                          Remainder of 3: anchor = 3
                    4) Century anchor: 2""";

        assertEquals(expected, dc.getCenturyAnchorDayCalculationSteps());
    }

    @Test
    void getCenturyAnchorDayCalculationStepsWithRemainderOne() {
        DoomsdayCalculator dc = new DoomsdayCalculator(LocalDate.of(2100, 1, 1));

        String expected =
                """
                    Century anchor calculation steps:
                    Year value: 2100
                    1) Divide the year by 100 to find the century: 2100 / 100 = 21
                    2) Find the remainder when the century is divided by 4: 21 % 4 = 1
                    3) Determine the century anchor based on the remainder:
                          Remainder of 0: anchor = 2
                          Remainder of 1: anchor = 0
                          Remainder of 2: anchor = 5
                          Remainder of 3: anchor = 3
                    4) Century anchor: 0""";

        assertEquals(expected, dc.getCenturyAnchorDayCalculationSteps());
    }

    @Test
    void getCenturyAnchorDayCalculationStepsWithRemainderTwo() {
        DoomsdayCalculator dc = new DoomsdayCalculator(LocalDate.of(2200, 1, 1));

        String expected =
                """
                    Century anchor calculation steps:
                    Year value: 2200
                    1) Divide the year by 100 to find the century: 2200 / 100 = 22
                    2) Find the remainder when the century is divided by 4: 22 % 4 = 2
                    3) Determine the century anchor based on the remainder:
                          Remainder of 0: anchor = 2
                          Remainder of 1: anchor = 0
                          Remainder of 2: anchor = 5
                          Remainder of 3: anchor = 3
                    4) Century anchor: 5""";

        assertEquals(expected, dc.getCenturyAnchorDayCalculationSteps());
    }

    @Test
    void getCenturyAnchorDayCalculationStepsWithRemainderThree() {
        DoomsdayCalculator dc = new DoomsdayCalculator(LocalDate.of(2300, 1, 1));

        String expected =
                """
                    Century anchor calculation steps:
                    Year value: 2300
                    1) Divide the year by 100 to find the century: 2300 / 100 = 23
                    2) Find the remainder when the century is divided by 4: 23 % 4 = 3
                    3) Determine the century anchor based on the remainder:
                          Remainder of 0: anchor = 2
                          Remainder of 1: anchor = 0
                          Remainder of 2: anchor = 5
                          Remainder of 3: anchor = 3
                    4) Century anchor: 3""";

        assertEquals(expected, dc.getCenturyAnchorDayCalculationSteps());
    }

    @Test
    void calculateDoomsdayDay() {
        dc1.setYear(2026);
        dc2.setYear(1);

        assertEquals(6, dc1.calculateDoomsdayDay());
        assertEquals(3, dc2.calculateDoomsdayDay());

        dc1.setYear(2000);
        dc2.setYear(1800);

        assertEquals(2, dc1.calculateDoomsdayDay());
        assertEquals(5, dc2.calculateDoomsdayDay());
    }

    @Test
    void getCalculationSteps() {
        DoomsdayCalculator dc = new DoomsdayCalculator(LocalDate.of(1981, 2, 6));

        String expected =
                """
                    Doomsday day calculation steps:
                    Century anchor (from prior calculations): 3
                    Year value: 1981
                    Last two digits of the year value: 81
                    1) How many times does the number 12 fit as a whole into the two last digits of the year number? 12 fits in 6 time(s)
                    2) What is the difference between the two last digits of the year number and the product of the multiples of 12 from calculation 1 (remainder)? 81 - 72 = 9
                    3) How many times does the number 4 fit into the result of calculation 2? 4 fits in 2 time(s)
                    4) Add everything together: 3 + 6 + 9 + 2 = 20
                    5) Divide by 7, the numbers added together in step 5: 20 % 7 = 6""";

        assertEquals(expected, dc.getCalculationSteps());
    }

    @Test
    void getCalculationStepsWith2026() {
        DoomsdayCalculator dc = new DoomsdayCalculator(LocalDate.of(2026, 10, 6));

        String expected =
                """
                    Doomsday day calculation steps:
                    Century anchor (from prior calculations): 2
                    Year value: 2026
                    Last two digits of the year value: 26
                    1) How many times does the number 12 fit as a whole into the two last digits of the year number? 12 fits in 2 time(s)
                    2) What is the difference between the two last digits of the year number and the product of the multiples of 12 from calculation 1 (remainder)? 26 - 24 = 2
                    3) How many times does the number 4 fit into the result of calculation 2? 4 fits in 0 time(s)
                    4) Add everything together: 2 + 2 + 2 + 0 = 6
                    5) Divide by 7, the numbers added together in step 5: 6 % 7 = 6""";

        assertEquals(expected, dc.getCalculationSteps());
    }

    @Test
    void getMonthDoomsday() {
        /* If the current date's day is greater than the amount of days in any month, this test will have an error
         * if set to LocalDate. For example, if the date were 9/29/2026, when the February test runs and calls
         * getMonthDoomsday, that method will call getMonth which tries to get the month of a valid LocalDate.
         * However, given that the day is set to the 29th, it would try to create a date at 2/29/2026
         * which is invalid in non-leap years. */

        dc1.setDay(14);

        dc1.setMonth(1);
        assertEquals(3, dc1.getMonthDoomsday());

        dc1.setMonth(2);
        assertEquals(28, dc1.getMonthDoomsday());

        dc1.setMonth(3);
        assertEquals(14, dc1.getMonthDoomsday());

        dc1.setMonth(4);
        assertEquals(4, dc1.getMonthDoomsday());

        dc1.setMonth(5);
        assertEquals(9, dc1.getMonthDoomsday());

        dc1.setMonth(6);
        assertEquals(6, dc1.getMonthDoomsday());

        dc1.setMonth(7);
        assertEquals(11, dc1.getMonthDoomsday());

        dc1.setMonth(8);
        assertEquals(8, dc1.getMonthDoomsday());

        dc1.setMonth(9);
        assertEquals(5, dc1.getMonthDoomsday());

        dc1.setMonth(10);
        assertEquals(10, dc1.getMonthDoomsday());

        dc1.setMonth(11);
        assertEquals(7, dc1.getMonthDoomsday());

        dc1.setMonth(12);
        assertEquals(12, dc1.getMonthDoomsday());
    }

    @Test
    void getMonthDoomsdayLeapYear() {
        dc1.setYear(2024);

        dc1.setMonth(1);
        assertEquals(4, dc1.getMonthDoomsday());

        dc1.setMonth(2);
        assertEquals(29, dc1.getMonthDoomsday());
    }


    @Test
    void calculateDateWeekdayNonLeapYear() {
        dc1.setYear(2026);
        dc1.setMonth(9);
        dc1.setDay(14);

        assertEquals("Monday, September 14, 2026", dc1.calculateDateWeekday());
        assertEquals("Monday, January 1, 1", dc2.calculateDateWeekday());
    }

    @Test
    void calculateDateWeekdayLeapYear() {
        dc1.setYear(2024);
        dc1.setMonth(2);
        dc1.setDay(29);

        assertEquals("Thursday, February 29, 2024", dc1.calculateDateWeekday());
    }

    @Test
    void compareTo() {
        dc1.setYear(2026);
        dc1.setMonth(9);
        dc1.setDay(14);

        dc2.setYear(2026);
        dc2.setMonth(9);
        dc2.setDay(19);

        assertEquals(-5, dc1.compareTo(dc2));

        dc1.setDay(19);
        assertEquals(0, dc1.compareTo(dc2));

        dc1.setDay(20);
        assertEquals(1, dc1.compareTo(dc2));
    }

    @Test
    void equals() {
        dc1.setYear(2026);
        dc1.setMonth(9);
        dc1.setDay(14);

        dc2.setYear(2026);
        dc2.setMonth(9);
        dc2.setDay(14);

        assertEquals(dc1, dc2);

        dc2.setDay(15);

        assertNotEquals(dc1, dc2);
    }
}
