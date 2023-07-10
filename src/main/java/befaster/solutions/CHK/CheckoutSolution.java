package befaster.solutions.CHK;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CheckoutSolution {
    public static final Map<Character, Integer> INDIVIDUAL_PRICES = new HashMap<>();
    public static final int SPECIAL_OFFER_A_FOR_3 = 130;
    public static final int SPECIAL_OFFER_A_FOR_5 = 200;
    public static final int SPECIAL_OFFER_B_FOR_2 = 45;
    static {
        INDIVIDUAL_PRICES.put('A', 50);
        INDIVIDUAL_PRICES.put('B', 30);
        INDIVIDUAL_PRICES.put('C', 20);
        INDIVIDUAL_PRICES.put('D', 15);
        INDIVIDUAL_PRICES.put('E', 40);
    }

    private Integer calculateTotalPrice(char item, Long count, Integer individualPrice, Map<Character, Long> productCount) {
        if (count == null || individualPrice == null || productCount == null) {
            return 0;
        }
        switch (item) {
            case 'A':
                return (int) (count / 5 * SPECIAL_OFFER_A_FOR_5
                        + count % 5 / 3 * SPECIAL_OFFER_A_FOR_3
                        + (count % 5) % 3 * individualPrice);
            case 'B':
                return (int) (count / 2 * SPECIAL_OFFER_B_FOR_2 + count % 2 * individualPrice);
            case 'E':
                Long numberOfBs = productCount.get('B');
                if (numberOfBs == null) {
                    return 0;
                }
                long numberOfBsValue = numberOfBs.longValue();
                long numberOfFreeBs = count / 2;
                long discount = numberOfBsValue <= numberOfFreeBs
                        ? calculateTotalPrice('B', numberOfBsValue, INDIVIDUAL_PRICES.get('B'), productCount)
                        : calculateTotalPrice('B', numberOfFreeBs, INDIVIDUAL_PRICES.get('B'), productCount);

                return (int) (count * individualPrice - discount);
            default:
                return (int) (count * individualPrice);
        }
    }

    private boolean isInvalidSkus(String skus) {
        if (skus == null) {
            return true;
        }
        boolean isInvalid = false;

        for (char c : skus.toCharArray()) {
            isInvalid = !INDIVIDUAL_PRICES.containsKey(c);
            if (isInvalid) {
                break;
            }
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
                    int mixMatchDiscount = 0;
                    if (individualPrice != null) {
                        price = calculateTotalPrice(key, productCount.get(key), individualPrice, productCount);
                    }
                    return price - mixMatchDiscount;
                })
                .sum();
    }
}





