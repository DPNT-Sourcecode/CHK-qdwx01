package befaster.solutions.CHK;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CheckoutSolution {
    public static final Map<Character, Integer> INDIVIDUAL_PRICES = new HashMap<>();
    public static final int SPECIAL_OFFER_A_FOR_3 = 130;
    public static final int SPECIAL_OFFER_B_FOR_2 = 45;
    static {
        INDIVIDUAL_PRICES.put('A', 50);
        INDIVIDUAL_PRICES.put('B', 30);
        INDIVIDUAL_PRICES.put('C', 20);
        INDIVIDUAL_PRICES.put('D', 15);
    }

    private Integer calculateTotalPrice(char item, Long productCount, Integer individualPrice) {
        switch (item) {
            case 'A':
                return (int) (productCount / 3 * SPECIAL_OFFER_A_FOR_3 + productCount % 3 * individualPrice);
            case 'B':
                return (int) (productCount / 2 * SPECIAL_OFFER_B_FOR_2 + productCount % 2 * individualPrice);
            default:
                return (int) (productCount * individualPrice);
        }
    }
    
    private boolean isInvalidSkus(String skus) {
        if (skus == null) {
            return true;
        }
        boolean isInvalid = false;

        for (char c : skus.toCharArray()) {
            isInvalid = !INDIVIDUAL_PRICES.containsKey(c);
        }

        return isInvalid;
    }

    public Integer checkout(String skus) {
        if (isInvalidSkus(skus)) {
            return -1;
        }
        Map<Character, Long> productCount = skus.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return productCount.keySet().stream()
                .mapToInt(key -> {
                    Integer individualPrice = INDIVIDUAL_PRICES.get(key);
                    int price = 0;
                    if (individualPrice != null) {
                        price = calculateTotalPrice(key, productCount.get(key), individualPrice);
                    }
                    return price;
                })
                .sum();
    }
}



