package yukihane.dq10don.account;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import yukihane.dq10don.communication.dto.tobatsu.TobatsuDataList;
import yukihane.dq10don.communication.dto.tobatsu.TobatsuDto;


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
    @Getter
    private Long id;

    @DatabaseField(foreign = true, uniqueCombo = true)
    @Setter
    private Character character;

    @DatabaseField(uniqueCombo = true)
    @Getter
    private int countySize;

    @DatabaseField(uniqueCombo = true)
    @Getter
    private String issuedDate;

    public TobatsuList() {
    }

    public TobatsuList(int countySize, String issuedDate) {
        this.countySize = countySize;
        this.issuedDate = issuedDate;
    }

    public static TobatsuList from(TobatsuDto dto, int countySize) {
        List<TobatsuDataList> tdls = dto.getCountryTobatsuDataList();
        for (TobatsuDataList tdl : tdls) {
            if (tdl.getCountySize() != countySize) {
                continue;
            }
            TobatsuList result = new TobatsuList(tdl.getCountySize(), tdl.getIssuedDate());
            for(yukihane.dq10don.communication.dto.tobatsu.TobatsuList tl : tdl.getTobatsuList()) {
                TobatsuItem ti = TobatsuItem.from(tl);
                result.addListItem(ti);
            }
            return result;
        }

        throw new IllegalArgumentException("countySize is not found: " + countySize);
    }

    public void addListItem(TobatsuItem item) {
        listItems.add(item);
        item.setList(this);
    }

    public List<TobatsuItem> getListItems() {
        return new ArrayList<>(listItems);
    }

}
