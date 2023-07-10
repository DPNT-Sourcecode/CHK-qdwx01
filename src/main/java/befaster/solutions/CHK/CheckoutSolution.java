package befaster.solutions.CHK;

import java.util.HashMap;
import java.util.Map;

public class CheckoutSolution {
    public static final Map<Character, Integer> INDIVIDUAL_PRICES = new HashMap<>();
    static {
        INDIVIDUAL_PRICES.put('A', 50);
        INDIVIDUAL_PRICES.put('B', 30);
        INDIVIDUAL_PRICES.put('C', 20);
        INDIVIDUAL_PRICES.put('D', 15);
    }

    public Integer checkout(String skus) {
        if (skus == null) {
            return -1;
        }
        return 0;
    }
}
