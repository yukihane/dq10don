package yukihane.dq10don.account;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
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

    @Getter
    @DatabaseField
    private String agencyStatus;

    @Getter
    @DatabaseField
    private String nearLimitDt;

    @Getter
    @DatabaseField(canBeNull = false)
    private boolean isFriendBlueBox;

    @Setter
    @Getter
    @DatabaseField(canBeNull = false)
    private Date lastUpdateDate;

    // O/R Mapper で必要
    public Farm() {
    }

    public Farm(String nextSailoutDt, String agencyStatus, String nearLimitDt, boolean isFriendBlueBox) {
        this.nextSailoutDt = nextSailoutDt;
        this.agencyStatus = agencyStatus;
        this.nearLimitDt = nearLimitDt;
        this.isFriendBlueBox = isFriendBlueBox;
    }

    public static Farm from(GameInfoDto dto) {
        Data data = dto.getData();
        AgencyInfo ai = data.getAgencyInfo();
        Farm res = new Farm(ai.getNextSailoutDt(), ai.getAgencyStatus(), ai.getNearLimitDt(), data.getIsFriendBlueBox());

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

    public List<FarmBox> getFarmBoxes() {
        return new ArrayList<>(farmBoxes);
    }
}
