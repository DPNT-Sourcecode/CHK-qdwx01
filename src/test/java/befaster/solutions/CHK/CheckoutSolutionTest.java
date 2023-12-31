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
    void shouldWorkForAllLetters() {
        int sum = checkoutSolution.checkout("ABCDE");

        assertEquals(155, sum);
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
        int sum = checkoutSolution.checkout("AAABCDEEEE");

        assertEquals(325, sum);
    }

    @Test
    void shouldWorkIfThereAreNoBs() {
        int sum = checkoutSolution.checkout("EE");

        assertEquals(80, sum);
    }

    @Test
    void shouldWorkIfLettersAreMixedTogether() {
        int sum = checkoutSolution.checkout("ABCDEABCDE");

        assertEquals(280, sum);
    }

    @Test
    void shouldWorkWithOffersForEAndB() {
        int sum = checkoutSolution.checkout("AAABBCDEEEE");

        assertEquals(325, sum);
    }

    @Test
    void shouldReturnMinus1IfIncludesProductNotOnTheList() {
        int sum = checkoutSolution.checkout("AxA");

        assertEquals(-1, sum);
    }

    @Test
    void shouldWorkWithFs() {
        int sum = checkoutSolution.checkout("FFABCDECBAABCABBAAAEEAAFF");

        assertEquals(695, sum);
    }

    @Test
    void shouldWorkForWholeAlphabetRange() {
        int sum = checkoutSolution.checkout("FFABCDECBAABCABBAAAEEAAFFKMNOPRRQXZ");

        assertEquals(1048, sum);
    }

    @Test
    void shouldWorkForCombinationsOfXYZ() {
        int sum = checkoutSolution.checkout("XYZXYZ");

        assertEquals(90, sum);
    }

    @Test
    void shouldWorkForCombinationsOfSZ() {
        int sum = checkoutSolution.checkout("SSSZ");

        assertEquals(65, sum);
    }

    @Test
    void shouldWorkForGroupOffers() {
        int sum = checkoutSolution.checkout("XYZXYZS");

        assertEquals(111, sum);
    }
}
