package yukihane.dq10don.account;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Date;
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

    /**
     * ドールのstorageId
     */
    public static final int STORAGE_ID_DOLL = 100;

    /**
     * 送った手紙の storageId
     */
    public static final int STORAGE_ID_SENDMAIL = 101;

    private final List<StoredItem> storedItems = new ArrayList<>();

    @DatabaseField(generatedId = true, canBeNull = false)
    @Setter
    @Getter
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

    @DatabaseField
    @Getter
    private Date lastUpdateDate;

    @DatabaseField(foreign = true, canBeNull = false)
    @Getter
    @Setter
    private Character character;

    public Storage() {
    }

    private Storage(int storageId, int storageIndex, String storageName) {
        this.storageId = storageId;
        this.storageIndex = storageIndex;
        this.storageName = storageName;
        this.lastUpdateDate = new Date();
    }

    public static Storage from(StorageListValueList dto) {
        return new Storage(dto.getStorageId(), dto.getStorageIndex(), dto.getStorageName());
    }

    public List<StoredItem> getSotredItems() {
        return new ArrayList<>(storedItems);
    }

    public void addStoredItem(StoredItem item) {
        storedItems.add(item);
        item.setStorage(this);
    }

    @Override
    public String toString() {
        return "Storage{" +
                "id=" + id +
                ", storageId=" + storageId +
                ", storageIndex=" + storageIndex +
                ", storageName='" + storageName + '\'' +
                ", lastUpdateDate=" + lastUpdateDate +
                ", character=" + character +
                '}';
    }
}
