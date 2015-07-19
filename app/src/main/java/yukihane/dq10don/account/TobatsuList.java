package yukihane.dq10don.account;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;


/**
 * {@link TobatsuItem} のまとまりです.
 */
@DatabaseTable
public class TobatsuList {


    /**
     * 小国
     */
    public static final int COUNTY_SIZE_SHOUKOKU = 1;
    /**
     * 大国
     */
    public static final int COUNTY_SIZE_TAIKOKU = 2;
    /**
     * (偽の)レンダーシア
     */
    public static final int COUNTY_SIZE_WEEKLY = 3;
    /**
     * 真のレンダーシア
     */
    public static final int COUNTY_SIZE_REALRENDASIA = 4;

    private final List<TobatsuItem> listItems = new ArrayList<>();

    @DatabaseField(id = true, generatedId = true)
    private Long id;

    @DatabaseField(foreign = true, uniqueCombo = true)
    private Character character;

    @DatabaseField(uniqueCombo = true)
    @Getter
    private int countySize;

    @DatabaseField(uniqueCombo = true)
    @Getter
    private String issuedDate;

    public TobatsuList() {
    }

    public void addListItem(TobatsuItem item) {
        listItems.add(item);
    }

    public List<TobatsuItem> getListItems() {
        return new ArrayList<>(listItems);
    }

}
