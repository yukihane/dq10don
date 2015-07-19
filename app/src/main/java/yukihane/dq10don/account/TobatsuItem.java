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
    private String monsterName;

    @DatabaseField
    @Getter
    private String condition;

    @DatabaseField
    @Getter
    private int point;

    private TobatsuItem() {
    }

    public TobatsuItem(String monsterName, String condition, int point) {
        this.monsterName = monsterName;
        this.condition = condition;
        this.point = point;
    }
}
