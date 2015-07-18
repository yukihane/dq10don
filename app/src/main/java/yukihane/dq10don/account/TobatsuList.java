package yukihane.dq10don.account;

import java.util.ArrayList;
import java.util.List;


public class TobatsuList {
    private final List<TobatsuItem> listings = new ArrayList<>();

    public void addListing(TobatsuItem item) {
        listings.add(item);
    }

    public List<TobatsuItem> getListings() {
        return new ArrayList<>(listings);
    }

}
