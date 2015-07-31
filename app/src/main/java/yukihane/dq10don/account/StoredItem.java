package yukihane.dq10don.account;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 持ち物.
 */
@DatabaseTable
public class StoredItem {

    @DatabaseField(id = true, canBeNull = false)
    private String itemUniqueNo;

    @DatabaseField(canBeNull = false)
    private String itemName;

    @DatabaseField
    private String webItemId;

    /**
     * カードの場合には残り時間が「あとx時間有効」という文面で入っています.
     */
    @DatabaseField
    private String variousStr;
}
