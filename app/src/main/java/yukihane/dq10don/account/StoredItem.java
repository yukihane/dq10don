package yukihane.dq10don.account;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.Setter;
import yukihane.dq10don.communication.dto.profile.ItemBasicListValueList;

/**
 * 持ち物.
 */
@DatabaseTable
public class StoredItem {


    // あと192時間有効
    // あと 52分 有効
    // 「分」の場合は半角スペースが前後に入っている
    private static final Pattern LEFT_TIME_PATTERN = Pattern.compile("あと ?(\\d+)([^ ]+) ?有効");

    @DatabaseField(generatedId = true, canBeNull = false)
    private Long id;

    @DatabaseField(canBeNull = false, uniqueCombo = true)
    private String itemUniqueNo;

    @DatabaseField(canBeNull = false)
    @Getter
    private String itemName;

    @DatabaseField
    private String webItemId;

    /**
     * カードの場合には残り時間が「あとx時間有効」という文面で入っています.
     */
    @DatabaseField
    @Getter
    private String variousStr;


    @DatabaseField(foreign = true, uniqueCombo = true)
    @Setter
    @Getter
    private Storage storage;

    public StoredItem() {

    }

    private StoredItem(String itemUniqueNo, String itemName, String webItemId, String variousStr) {
        this.itemUniqueNo = itemUniqueNo;
        this.itemName = itemName;
        this.webItemId = webItemId;
        this.variousStr = variousStr;
    }


    public static StoredItem from(ItemBasicListValueList dto) {
        return new StoredItem(dto.getItemUniqueNo(), dto.getItemName(), dto.getWebItemId(), dto.getVariousStr());
    }

    /**
     * 残り時間を返します. 単位は分です.
     *
     * @return 残り時間. 残り時間が設定されていないアイテムの場合はnull.
     */
    public Integer getLeftTime() {
        if (variousStr == null) {
            return null;
        }
        Matcher m = LEFT_TIME_PATTERN.matcher(variousStr);
        if (m.matches()) {
            String numStr = m.group(1);
            String unit = m.group(2);
            int num = Integer.parseInt(numStr);

            // 「時間」意外の単位は「分」しか無いはず…
            if ("時間".equals(unit)) {
                return num * 60;
            } else {
                return num;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "StoredItem{" +
                "itemUniqueNo='" + itemUniqueNo + '\'' +
                ", itemName='" + itemName + '\'' +
                ", webItemId='" + webItemId + '\'' +
                ", variousStr='" + variousStr + '\'' +
                '}';
    }
}
