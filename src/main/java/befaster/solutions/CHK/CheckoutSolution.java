package befaster.solutions.CHK;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CheckoutSolution {
    public static final Map<Character, Integer> INDIVIDUAL_PRICES = new HashMap<>();
    public static final Character[] GROUP_OFFER = {'X', 'S', 'T', 'Y', 'Z'};
    public static final int GROUP_OFFER_VALUE = 45;
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

    /**
     * Calculates total price for given item type. It invokes method to calculate any special offers.
     *
     * @param item            item type as a char
     * @param count           number of items
     * @param individualPrice individual price of the item
     * @return total price given item and count.
     */
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

    /**
     * It checks if skus is invalid.
     *
     * @param skus a String containing the SKUs of all the products in the basket
     * @return true if invalid skus
     */
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

    /**
     * Gets number of group offer available in the checkout
     *
     * @param productCount map that includes all items in the checkout
     * @return number of group offers available
     */
    private int getNumberOfGroupOffers(Map<Character, Long> productCount) {
        long sum = Arrays.stream(GROUP_OFFER)
                .mapToLong(key -> {
                    if (productCount.containsKey(key)) {
                        return productCount.get(key);
                    }
                    return 0L;
                })
                .reduce(Long::sum)
                .orElse(0L);
        return (int) (sum / 3);
    }

    /**
     * Applies group
     * @param numberOfCombinations number of group offers available
     * @param productCount number of group offers available
     */
    private void applyGroupItemOffers(long numberOfCombinations, Map<Character, Long> productCount) {
        int j = 0;
        int currentCombination = 1;
        while (j < GROUP_OFFER.length && currentCombination <= numberOfCombinations) {
            Long count = productCount.get(GROUP_OFFER[j]);
            if (productCount.containsKey(GROUP_OFFER[j]) && count > 0) {
                productCount.put(GROUP_OFFER[j], count - 1);
                if (j % 3 == 0 && j > 0) {
                    currentCombination++;
                }
            } else {
                j++;
            }
        }
    }

    /**
     * Calculates number of freeItems when a number of items is bought.
     * Works in cases when:
     * 2E get one B free, where E is the item, B is free item and 2 is the number
     * 3U get one U free, where U is both item and free item and 3 is the number
     *
     * @param item         items that need to be bought to get a free item
     * @param freeItem     free item that is handed out when number of items is bought
     * @param number       number of items that needs to be bought to get free item
     * @param productCount map that includes all items in the checkout
     * @return number of free items
     */
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

    /**
     * Updates product count by reducing it by number of free items.
     *
     * @param productCount map with items and their respective count
     */
    private void applyFreeItemsOffers(Map<Character, Long> productCount) {
        Map<Character, Long> freeItems = new HashMap<>();

        freeItems.put('B', getNumberOfFreeItems('E', 'B', 2, productCount));
        freeItems.put('F', getNumberOfFreeItems('F', 'F', 2, productCount));
        freeItems.put('M', getNumberOfFreeItems('N', 'M', 3, productCount));
        freeItems.put('Q', getNumberOfFreeItems('R', 'Q', 3, productCount));
        freeItems.put('U', getNumberOfFreeItems('U', 'U', 3, productCount));

        productCount.keySet()
                .forEach(key -> {
                    if (freeItems.containsKey(key)) {
                        productCount.put(key, Math.max(productCount.get(key) - freeItems.get(key), 0));
                    }
                });
    }

    /**
     * Checkout method that calculates total price.
     *
     * @param skus a String containing the SKUs of all the products in the basket
     * @return an Integer representing the total checkout value of the items
     */
    public Integer checkout(String skus) {
        if (isInvalidSkus(skus)) {
            return -1;
        }
        Map<Character, Long> productCount = skus.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        applyFreeItemsOffers(productCount);

        int numberOfGroupOffers = getNumberOfGroupOffers(productCount);
        applyGroupItemOffers(numberOfGroupOffers, productCount);

        return productCount.keySet().stream()
                .mapToInt(key -> {
                    Integer individualPrice = INDIVIDUAL_PRICES.get(key);
                    int price = 0;
                    if (individualPrice != null) {
                        price = calculateTotalPrice(key, productCount.get(key), individualPrice);
                    }
                    return price;
                })
                .sum() + GROUP_OFFER_VALUE * numberOfGroupOffers;
    }

    /**
     * Record that describes and offer in form of: Buy {number} of items to get them for {value}.
     * Example: 2B for 45
     *
     * @param number number of items in the offer
     * @param value  total value of the offer
     */
    private record Offer(int number, int value) {
        /**
         * Calculate total offer value based on number of items and individual price.
         *
         * @param count           total number
         * @param individualPrice individual price of the offer
         * @return total offer value
         */
        public int calculateOffer(long count, int individualPrice) {
            return (int) (count / this.number * this.value + count % this.number * individualPrice);
        }
    }

    /**
     * Record that describes a double offer in form of: Buy {largeNumber} of items to get them for {largeValue} or buy {smallNumber} of items to get them for {smallValue}.
     * <p>
     * These offers can be combined.
     * <p>
     * Example: 3A for 130, 5A for 200
     *
     * @param largeNumber large number of items in the offer
     * @param majorValue  major offer value for large number of items
     * @param smallNumber small number of items in the offer
     * @param minorValue  minor offer value for small number of items
     */
    private record DoubleOffer(int largeNumber, int majorValue, int smallNumber, int minorValue) {
        /**
         * Calculate total offer value based on number of items and individual price.
         *
         * @param count           total number
         * @param individualPrice individual price of the offer
         * @return total offer value
         */
        public int calculateOffer(long count, int individualPrice) {
            return (int) (count / largeNumber * majorValue
                    + count % largeNumber / smallNumber * minorValue
                    + (count % largeNumber) % smallNumber * individualPrice);
        }
    }
}






