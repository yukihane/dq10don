package yukihane.dq10don.account;

/**
 * Created by yuki on 15/07/08.
 */
public class TobatsuItem {
    private String targetName;
    private String condition;
    private int point;

    public TobatsuItem(String targetName, String condition, int point) {
        this.targetName = targetName;
        this.condition = condition;
        this.point = point;
    }

    public String getTargetName() {
        return targetName;
    }

    public String getCondition() {
        return condition;
    }

    public int getPoint() {
        return point;
    }
}
