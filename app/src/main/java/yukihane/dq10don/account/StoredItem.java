package yukihane.dq10don.account;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import lombok.Getter;
import lombok.Setter;
import yukihane.dq10don.communication.dto.profile.ItemBasicListValueList;

/**
 * 持ち物.
 */
@DatabaseTable
public class StoredItem {

    @DatabaseField(id = true, canBeNull = false)
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


    @DatabaseField(foreign = true)
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
}
