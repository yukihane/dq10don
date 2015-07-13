package yukihane.dq10don.account;

import java.util.ArrayList;
import java.util.List;


public class TobatsuList {
    private final List<TobatsuItem> acceptings = new ArrayList<>();
    private final List<TobatsuItem> listings = new ArrayList<>();

    public void addAccepting(TobatsuItem item) {
        acceptings.add(item);
    }

    public void addListing(TobatsuItem item) {
        listings.add(item);
    }

    public List<TobatsuItem> getAcceptings() {
        return new ArrayList<>(acceptings);
    }

    public List<TobatsuItem> getListings() {
        return new ArrayList<>(listings);
    }

}
