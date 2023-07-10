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

    private Integer calculateTotalPrice(char item, Long count, Integer individualPrice) {
        switch (item) {
            case 'A':
                return (int) (count / 5 * SPECIAL_OFFER_A_FOR_5
                        + count % 5 / 3 * SPECIAL_OFFER_A_FOR_3
                        + (count % 5) % 3 * individualPrice);
            case 'B':
                return (int) (count / 2 * SPECIAL_OFFER_B_FOR_2 + count % 2 * individualPrice);
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

    private Map<Character, Long> applyFreeItemsOffers(Map<Character, Long> productCount) {
        Map<Character, Long> productCountWithoutFreeItems = new HashMap<>(productCount);

        long numberOfFreeBs = productCount.get('E') != null ? productCount.get('E') / 2 : 0;

        if (productCountWithoutFreeItems.containsKey('B')) {
            productCountWithoutFreeItems.put('B', productCountWithoutFreeItems.get('B') - numberOfFreeBs);
        }

        return productCountWithoutFreeItems;
    }

    public Integer checkout(String skus) {
        if (isInvalidSkus(skus)) {
            return -1;
        }
        Map<Character, Long> productCount = skus.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Map<Character, Long> productCountWithoutFreeItems = applyFreeItemsOffers(productCount);

        return productCountWithoutFreeItems.keySet().stream()
                .mapToInt(key -> {
                    Integer individualPrice = INDIVIDUAL_PRICES.get(key);
                    int price = 0;
                    int mixMatchDiscount = 0;
                    if (individualPrice != null) {
                        price = calculateTotalPrice(key, productCountWithoutFreeItems.get(key), individualPrice);
                    }
                    return price - mixMatchDiscount;
                })
                .sum();
    }
}

