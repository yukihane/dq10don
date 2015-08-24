package yukihane.dq10don.account;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import yukihane.dq10don.Utils;
import yukihane.dq10don.communication_game.dto.farm.info.AgencyInfo;
import yukihane.dq10don.communication_game.dto.farm.info.Data;
import yukihane.dq10don.communication_game.dto.farm.info.GameInfoDto;
import yukihane.dq10don.communication_game.dto.farm.info.GrassList;
import yukihane.dq10don.communication_game.dto.farm.info.TreasureboxList;

/**
 * Created by yuki on 2015/08/17.
 */
@DatabaseTable
public class Farm {

    private static final String AGENCY_PR = "PR";
    private static final String AGENCY_RE = "RE";


    private final List<FarmGrass> farmGrasses = new ArrayList<>();

    private final List<FarmBox> farmBoxes = new ArrayList<>();

    @Setter
    @Getter
    @DatabaseField(generatedId = true, canBeNull = false)
    private Long id;

    @Setter
    @Getter
    @DatabaseField(foreign = true, unique = true, canBeNull = false)
    private Character character;

    @Getter
    @DatabaseField
    private String nextSailoutDt;

    /**
     * RE: 出航可能, PR: 準備中(?)
     */
    @DatabaseField
    private String agencyStatus;

    /**
     * 船旅報酬の未取得件数.
     */
    @Getter
    @DatabaseField(canBeNull = false)
    private int ungetCount;

    @Getter
    @DatabaseField
    private String nearLimitDt;

    @Getter
    @DatabaseField(canBeNull = false)
    private boolean isFriendBlueBox;

    /**
     * 得られた情報以上に宝箱がある場合はtrue(と思われる)
     */
    @Getter
    @DatabaseField(canBeNull = false)
    private boolean moreRebirthTreasureBox;

    @Setter
    @Getter
    @DatabaseField(canBeNull = false)
    private Date lastUpdateDate;

    // O/R Mapper で必要
    public Farm() {
    }

    public Farm(String nextSailoutDt, String agencyStatus, Integer ungetCount, String nearLimitDt,
                boolean isFriendBlueBox, boolean moreRebirthTreasureBox) {
        this.nextSailoutDt = nextSailoutDt;
        this.agencyStatus = agencyStatus;
        this.ungetCount = (ungetCount == null) ? 0 : ungetCount.intValue();
        this.nearLimitDt = nearLimitDt;
        this.isFriendBlueBox = isFriendBlueBox;
        this.moreRebirthTreasureBox = moreRebirthTreasureBox;
    }

    public static Farm from(GameInfoDto dto) {
        Data data = dto.getData();
        AgencyInfo ai = data.getAgencyInfo();
        Farm res = new Farm(ai.getNextSailoutDt(), ai.getAgencyStatus(), ai.getUngetCount(),
                ai.getNearLimitDt(), data.getIsFriendBlueBox(), data.getMoreRebirthTreasureBox());

        List<GrassList> grasses = data.getGrassList();
        for (GrassList g : grasses) {
            FarmGrass grass = FarmGrass.from(g);
            res.addGrass(grass);
        }

        List<TreasureboxList> boxes = data.getTreasureboxList();
        for (TreasureboxList b : boxes) {
            FarmBox box = FarmBox.from(b);
            res.addBox(box);
        }

        return res;
    }

    /**
     * 出航可能(船旅可能)ならtrue
     */
    public boolean isUnanchorable() {
        if (AGENCY_RE.equals(agencyStatus)) {
            return true;
        }
        return false;
    }

    public void addGrass(FarmGrass obj) {
        farmGrasses.add(obj);
        obj.setFarm(this);
    }

    public void addBox(FarmBox obj) {
        farmBoxes.add(obj);
        obj.setFarm(this);
    }

    public List<FarmGrass> getFarmGrasses() {
        return new ArrayList<>(farmGrasses);
    }

    public int getFarmGrassSize() {
        return farmGrasses.size();
    }

    public List<FarmBox> getFarmBoxes() {
        return new ArrayList<>(farmBoxes);
    }

    public int getFarmBoxSize() {
        return farmBoxes.size();
    }

    /**
     * @return 牧場にある宝箱のうち、最も近い期限. 無ければnull.
     */
    public Date getFarmBoxNearestLimit() {
        if (farmBoxes.isEmpty()) {
            return null;
        }
        List<FarmBox> list = new ArrayList<>(farmBoxes);
        Collections.sort(list);
        String dt = list.get(0).getExpiredDt();
        return Utils.parseDate(dt);
    }
}
