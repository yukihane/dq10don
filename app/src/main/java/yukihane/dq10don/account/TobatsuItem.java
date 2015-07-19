package yukihane.dq10don.account;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import lombok.Getter;
import lombok.Setter;

@DatabaseTable
public class TobatsuItem {

    @DatabaseField(id = true, generatedId = true)
    private Long id;

    @DatabaseField(foreign = true)
    @Setter
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

    public static TobatsuItem from(yukihane.dq10don.communication.dto.tobatsu.TobatsuList tl) {
        return new TobatsuItem(tl.getMonsterName(), tl.getArea(), tl.getPoint());
    }
}
