package yukihane.dq10don.farm.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import lombok.Getter;
import yukihane.dq10don.communication_game.dto.farm.mowgrass.ItemList;

import static yukihane.dq10don.communication_game.Constants.ID_EXP;
import static yukihane.dq10don.communication_game.Constants.ID_MEDAL;

/**
 * Created by yuki on 15/08/25.
 */
public class MowResult {

    public static final MowResult EMPTY = new MowResult(0, 0, 0);

    private static final Logger LOGGER = LoggerFactory.getLogger(MowResult.class);

    @Getter
    private final int medalCount;
    @Getter
    private final int expCount;
    @Getter
    private final int otherCount;

    MowResult(int medalCount, int expCount, int otherCount) {
        this.medalCount = medalCount;
        this.expCount = expCount;
        this.otherCount = otherCount;
    }

    public static MowResult from(List<ItemList> items) {
        int medalCount = 0;
        int expCount = 0;
        int otherCount = 0;

        for (ItemList i : items) {
            String iid = i.getItemId();
            if (ID_MEDAL.equals(iid)) {
                medalCount += i.getCount();
            } else if (ID_EXP.equals(iid)) {
                expCount += i.getCount();
            } else {
                LOGGER.info("unexpected itemId of farm grass: {}", i.getItemId());
                otherCount += i.getCount();
            }
        }

        return new MowResult(medalCount, expCount, otherCount);
    }
}
