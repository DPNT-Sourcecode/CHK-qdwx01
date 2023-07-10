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

    }
}