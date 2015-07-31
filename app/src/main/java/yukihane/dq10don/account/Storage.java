package yukihane.dq10don.account;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

/**
 * 持ち物({@link StoredItem})をいれる容器.
 * (strageId, storageIndex)が自然キーになります.
 */
@DatabaseTable
public class Storage {

    /**
     * 種別(装備欄、とか、倉庫、とか)の識別子.
     */
    @DatabaseField(canBeNull = false)
    private int storageId;

    /**
     * 同一種別の中の個体識別番号.
     */
    @DatabaseField(canBeNull = false)
    private int storageIndex;

    @DatabaseField(foreign = true, canBeNull = false)
    private Character character;

    @DatabaseField
    private String storageName;

    private List<StoredItem> storedItems;
}
