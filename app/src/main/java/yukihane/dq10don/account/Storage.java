package yukihane.dq10don.account;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import yukihane.dq10don.communication.dto.profile.StorageListValueList;

/**
 * 持ち物({@link StoredItem})をいれる容器.
 * (strageId, storageIndex)が自然キーになります.
 */
@DatabaseTable
public class Storage {


    @DatabaseField(generatedId = true, canBeNull = false)
    private Long id;

    /**
     * 種別(装備欄、とか、倉庫、とか)の識別子.
     */
    @DatabaseField(canBeNull = false)
    @Getter
    private int storageId;

    /**
     * 同一種別の中の個体識別番号.
     */
    @DatabaseField(canBeNull = false)
    @Getter
    private int storageIndex;

    @DatabaseField
    @Getter
    private String storageName;

    @DatabaseField(foreign = true, canBeNull = false)
    @Getter
    @Setter
    private Character character;

    private final List<StoredItem> storedItems = new ArrayList<>();

    public Storage() {
    }

    public Storage(int storageId, int storageIndex, String storageName) {
        this.storageId = storageId;
        this.storageIndex = storageIndex;
        this.storageName = storageName;
    }

    public static Storage from(StorageListValueList dto) {
        return new Storage(dto.getStorageId(), dto.getStorageIndex(), dto.getStorageName());
    }

    public List<StoredItem> getSotredItems() {
        return new ArrayList<>(storedItems);
    }

    public void addItem(StoredItem item) {
        storedItems.add(item);
        item.setStorage(this);
    }
}
