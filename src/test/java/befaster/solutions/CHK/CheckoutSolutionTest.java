package befaster.solutions.CHK;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutSolutionTest {
    CheckoutSolution checkoutSolution = new CheckoutSolution();

    @Test
    void shouldReturnMinusOneIfNull() {
        int sum = checkoutSolution.checkout(null);

        assertEquals(-1, sum);
    }

    @Test
    void shouldWorkForEmptyString() {
        int sum = checkoutSolution.checkout("");

        assertEquals(0, sum);
    }

    @Test
    void shouldReturnCorrectSumIfNoOffersApply() {
        int sum = checkoutSolution.checkout("AABCCCD");

        assertEquals(205, sum);
    }


    @Test
    void shouldWorkWithSpecialOffersForA() {
        int sum = checkoutSolution.checkout("AAAABCCCD");

        assertEquals(285, sum);
    }


    @Test
    void shouldWorkWithSpecialOffersForB() {
        int sum = checkoutSolution.checkout("AABBBBCD");

        assertEquals(225, sum);
    }

    @Test
    void shouldCombineTheOffers() {
        int sum = checkoutSolution.checkout("AAAAAABBBBCD");

        assertEquals(385, sum);
    }

    @Test
    void shouldReturnMinus1IfIncludesProductNotOnTheList() {
        int sum = checkoutSolution.checkout("AAAAZ|AXXABBBBCDEEFF___99");

        assertEquals(-1, sum);
    }
}