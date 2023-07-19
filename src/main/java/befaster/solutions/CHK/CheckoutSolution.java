package befaster.solutions.CHK;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CheckoutSolution {
    public static final Map<Character, Integer> INDIVIDUAL_PRICES = new HashMap<>();
    public static final DoubleOffer SPECIAL_OFFER_A = new DoubleOffer(5, 200, 3, 130);
    public static final Offer SPECIAL_OFFER_B = new Offer(2, 45);
    public static final DoubleOffer SPECIAL_OFFER_H = new DoubleOffer(10, 80, 5, 45);
    public static final Offer SPECIAL_OFFER_K = new Offer(2, 150);
    public static final Offer SPECIAL_OFFER_P = new Offer(5, 200);
    public static final Offer SPECIAL_OFFER_Q = new Offer(3, 80);
    public static final DoubleOffer SPECIAL_OFFER_V = new DoubleOffer(3, 130, 2, 90);

    static {
        INDIVIDUAL_PRICES.put('A', 50);
        INDIVIDUAL_PRICES.put('B', 30);
        INDIVIDUAL_PRICES.put('C', 20);
        INDIVIDUAL_PRICES.put('D', 15);
        INDIVIDUAL_PRICES.put('E', 40);
        INDIVIDUAL_PRICES.put('F', 10);
        INDIVIDUAL_PRICES.put('G', 20);
        INDIVIDUAL_PRICES.put('H', 10);
        INDIVIDUAL_PRICES.put('I', 35);
        INDIVIDUAL_PRICES.put('J', 60);
        INDIVIDUAL_PRICES.put('K', 70);
        INDIVIDUAL_PRICES.put('L', 90);
        INDIVIDUAL_PRICES.put('M', 15);
        INDIVIDUAL_PRICES.put('N', 40);
        INDIVIDUAL_PRICES.put('O', 10);
        INDIVIDUAL_PRICES.put('P', 50);
        INDIVIDUAL_PRICES.put('Q', 30);
        INDIVIDUAL_PRICES.put('R', 50);
        INDIVIDUAL_PRICES.put('S', 20);
        INDIVIDUAL_PRICES.put('T', 20);
        INDIVIDUAL_PRICES.put('U', 40);
        INDIVIDUAL_PRICES.put('V', 50);
        INDIVIDUAL_PRICES.put('W', 20);
        INDIVIDUAL_PRICES.put('X', 17);
        INDIVIDUAL_PRICES.put('Y', 20);
        INDIVIDUAL_PRICES.put('Z', 21);
    }

    private Integer calculateTotalPrice(char item, Long count, Integer individualPrice) {
        switch (item) {
            case 'A':
                return SPECIAL_OFFER_A.calculateOffer(count, individualPrice);
            case 'B':
                return SPECIAL_OFFER_B.calculateOffer(count, individualPrice);
            case 'H':
                return SPECIAL_OFFER_H.calculateOffer(count, individualPrice);
            case 'K':
                return SPECIAL_OFFER_K.calculateOffer(count, individualPrice);
            case 'P':
                return SPECIAL_OFFER_P.calculateOffer(count, individualPrice);
            case 'Q':
                return SPECIAL_OFFER_Q.calculateOffer(count, individualPrice);
            case 'V':
                return SPECIAL_OFFER_V.calculateOffer(count, individualPrice);
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

    private long getNumberOfFreeItems(char item, char freeItem, int number, Map<Character, Long> productCount) {
        if (productCount.get(item) == null || productCount.get(freeItem) == null) {
            return 0;
        }
        if (item == freeItem) {
            return productCount.get(freeItem) / (number + 1);
        } else {
            return productCount.get(item) / number;
        }
    }


    private Map<Character, Long> applyFreeItemsOffers(Map<Character, Long> productCount) {
        Map<Character, Long> productCountWithoutFreeItems = new HashMap<>(productCount);
        Map<Character, Long> freeItems = new HashMap<>();

        freeItems.put('B', getNumberOfFreeItems('E', 'B', 2, productCount));
        freeItems.put('F', getNumberOfFreeItems('F', 'F', 2, productCount));
        freeItems.put('M', getNumberOfFreeItems('N', 'M', 3, productCount));
        freeItems.put('Q', getNumberOfFreeItems('R', 'Q', 3, productCount));
        freeItems.put('U', getNumberOfFreeItems('U', 'U', 3, productCount));

        productCountWithoutFreeItems.keySet()
                .forEach(key -> {
                    if (freeItems.containsKey(key)) {
                        productCountWithoutFreeItems.put(key, Math.max(productCountWithoutFreeItems.get(key) - freeItems.get(key), 0));
                    }
                });

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

    private record Offer(int number, int price) {
        public int calculateOffer(long count, int individualPrice) {
            return (int) (count / this.number * this.price + count % this.number * individualPrice);
        }
    }

    private record DoubleOffer(int largeNumber, int largePrice, int smallNumber, int smallPrice) {
        public int calculateOffer(long count, int individualPrice) {
            return (int) (count / largeNumber * largePrice
                    + count % largeNumber / smallNumber * smallPrice
                    + (count % largeNumber) % smallNumber * individualPrice);
        }
    }
}
