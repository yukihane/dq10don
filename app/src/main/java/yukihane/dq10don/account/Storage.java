package yukihane.dq10don.account;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

/**
 * 持ち物({@link StoredItem})をいれる容器.
 */
@DatabaseTable
public class Storage {

    @DatabaseField(id = true, canBeNull = false)
    private Long id;

    @DatabaseField(canBeNull = false, uniqueCombo = true)
    private int storageId;

    @DatabaseField(canBeNull = false, uniqueCombo = true)
    private int storageIndex;

    @DatabaseField(foreign = true, canBeNull = false)
    private Character character;

    @DatabaseField
    private String storageName;

    private List<StoredItem> storedItems;
}
