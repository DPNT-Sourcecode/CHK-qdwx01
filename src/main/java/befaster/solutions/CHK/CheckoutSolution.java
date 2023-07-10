package befaster.solutions.CHK;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CheckoutSolution {
    public static final Map<Character, Integer> INDIVIDUAL_PRICES = new HashMap<>();
    public static final Offer SPECIAL_OFFER_A_FOR_3 = new Offer(3, 130);
    public static final Offer SPECIAL_OFFER_A_FOR_5 = new Offer(5, 200);
    public static final Offer SPECIAL_OFFER_B = new Offer(2, 45);
    public static final Offer SPECIAL_OFFER_H_FOR_5 = new Offer(5, 45);
    public static final Offer SPECIAL_OFFER_H_FOR_10 = new Offer(10, 80);
    public static final Offer SPECIAL_OFFER_K = new Offer(2, 150);
    public static final Offer SPECIAL_OFFER_P = new Offer(5, 200);
    public static final Offer SPECIAL_OFFER_Q = new Offer(3, 80);
    public static final Offer SPECIAL_OFFER_V_FOR_2 = new Offer(2, 90);
    public static final Offer SPECIAL_OFFER_V_FOR_3 = new Offer(3, 130);

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
        INDIVIDUAL_PRICES.put('K', 80);
        INDIVIDUAL_PRICES.put('L', 90);
        INDIVIDUAL_PRICES.put('M', 15);
        INDIVIDUAL_PRICES.put('N', 40);
        INDIVIDUAL_PRICES.put('O', 10);
        INDIVIDUAL_PRICES.put('P', 50);
        INDIVIDUAL_PRICES.put('Q', 30);
        INDIVIDUAL_PRICES.put('R', 50);
        INDIVIDUAL_PRICES.put('S', 30);
        INDIVIDUAL_PRICES.put('T', 20);
        INDIVIDUAL_PRICES.put('U', 40);
        INDIVIDUAL_PRICES.put('V', 50);
        INDIVIDUAL_PRICES.put('W', 20);
        INDIVIDUAL_PRICES.put('X', 90);
        INDIVIDUAL_PRICES.put('Y', 10);
        INDIVIDUAL_PRICES.put('Z', 50);
    }

    private Integer calculateTotalPrice(char item, Long count, Integer individualPrice) {
        switch (item) {
            case 'A':
                return (int) (count / SPECIAL_OFFER_A_FOR_5.getNumber() * SPECIAL_OFFER_A_FOR_5.getPrice()
                        + count %  SPECIAL_OFFER_A_FOR_5.getNumber() / SPECIAL_OFFER_A_FOR_3.getNumber() * SPECIAL_OFFER_A_FOR_3.getPrice()
                        + (count % SPECIAL_OFFER_A_FOR_5.getNumber()) % SPECIAL_OFFER_A_FOR_3.getNumber() * individualPrice);
            case 'B':
                return SPECIAL_OFFER_B.calculateOffer(count, individualPrice);
            case 'H':
                return (int) (count / SPECIAL_OFFER_H_FOR_10.getNumber() * SPECIAL_OFFER_H_FOR_10.getPrice()
                        + count %  SPECIAL_OFFER_H_FOR_10.getNumber() / SPECIAL_OFFER_H_FOR_5.getNumber() * SPECIAL_OFFER_H_FOR_5.getPrice()
                        + (count % SPECIAL_OFFER_H_FOR_10.getNumber()) % SPECIAL_OFFER_H_FOR_5.getNumber() * individualPrice);
            case 'K':
                return SPECIAL_OFFER_K.calculateOffer(count, individualPrice);
            case 'P':
                return SPECIAL_OFFER_P.calculateOffer(count, individualPrice);
            case 'Q':
                return SPECIAL_OFFER_Q.calculateOffer(count, individualPrice);
            case 'V':
                return (int) (count / SPECIAL_OFFER_V_FOR_3.getNumber() * SPECIAL_OFFER_V_FOR_3.getPrice()
                        + count %  SPECIAL_OFFER_V_FOR_3.getNumber() / SPECIAL_OFFER_V_FOR_2.getNumber() * SPECIAL_OFFER_V_FOR_2.getPrice()
                        + (count % SPECIAL_OFFER_V_FOR_3.getNumber()) % SPECIAL_OFFER_V_FOR_2.getNumber() * individualPrice);
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

        if (productCount.get('B') != null && productCount.get('E') != null) {
            long numberOfFreeBs = productCount.get('E') / 2;
            long numberOfBs = productCount.get('B');
            productCountWithoutFreeItems.put('B', Math.max(0, numberOfBs - numberOfFreeBs));
        }

        if (productCount.get('F') != null) {
            long numberOfFs = productCount.get('F');
            long numberOfFreeFs = productCount.get('F') / 3;

            productCountWithoutFreeItems.put('F', numberOfFs - numberOfFreeFs);
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

    private static class Offer {
        private final int number;
        private final int price;

        public Offer(int number, int price) {
            this.number = number;
            this.price = price;
        }

        public int getNumber() {
            return number;
        }

        public int getPrice() {
            return price;
        }

        public int calculateOffer(long count, int individualPrice) {
            return (int) (count / this.number * this.price + count % this.number * individualPrice);
        }
    }
}


