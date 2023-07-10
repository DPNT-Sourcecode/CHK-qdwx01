package befaster.solutions.CHK;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CheckoutSolution {
    public static final Map<Character, Integer> INDIVIDUAL_PRICES = new HashMap<>();
    static {
        INDIVIDUAL_PRICES.put('A', 50);
        INDIVIDUAL_PRICES.put('B', 30);
        INDIVIDUAL_PRICES.put('C', 20);
        INDIVIDUAL_PRICES.put('D', 15);
    }

    private Integer calculateSpecialOffer(char ) {

    }

    public Integer checkout(String skus) {
        if (skus == null) {
            return -1;
        }
        String skusUpperCase = skus.toUpperCase();
        Map<Character, Long> productCount = skusUpperCase.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return productCount.keySet().stream()
                .mapToInt(key -> {
                    Integer individualPrice = INDIVIDUAL_PRICES.get(key);
                    int price = 0;
                    if (individualPrice != null) {
                        price = (int) (productCount.get(key) * individualPrice);
                    }
                    return price;
                })
                .sum();
    }
}


