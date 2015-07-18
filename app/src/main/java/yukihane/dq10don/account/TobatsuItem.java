package yukihane.dq10don.account;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import lombok.Getter;

@DatabaseTable
public class TobatsuItem {

    @DatabaseField(id = true, generatedId = true)
    private Long id;

    @DatabaseField(foreign = true)
    private TobatsuList list;

    @DatabaseField
    @Getter
    private String targetName;

    @DatabaseField
    @Getter
    private String condition;

    @DatabaseField
    @Getter
    private int point;

    private TobatsuItem() {
    }

    public TobatsuItem(String targetName, String condition, int point) {
        this.targetName = targetName;
        this.condition = condition;
        this.point = point;
    }
}
