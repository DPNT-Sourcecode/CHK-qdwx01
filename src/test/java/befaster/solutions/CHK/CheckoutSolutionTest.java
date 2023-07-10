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
    void shouldCombineTheOffersFor3AAnd2B() {
        int sum = checkoutSolution.checkout("AAAABBBBCD");

        assertEquals(305, sum);
    }

    @Test
    void shouldCombineOffersForAs() {
        int sum = checkoutSolution.checkout("AAAAAAAAAAAAAAAAAAA");

        assertEquals(780, sum);
    }

    @Test
    void shouldWorkWithOffersForE() {
        int sum = checkoutSolution.checkout("AABBBBCDEEEE");

        assertEquals(780, sum);
    }

    @Test
    void shouldReturnMinus1IfIncludesProductNotOnTheList() {
        int sum = checkoutSolution.checkout("AxA");

        assertEquals(-1, sum);
    }
}
