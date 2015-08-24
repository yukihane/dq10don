package yukihane.dq10don.account;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import lombok.Getter;
import lombok.Setter;
import yukihane.dq10don.communication_game.dto.farm.info.GrassList;

/**
 * モンスター牧場の牧草
 */
@DatabaseTable
public class FarmGrass {

    @DatabaseField(generatedId = true, canBeNull = false)
    private Long id;

    @Getter
    @DatabaseField(canBeNull = false)
    private long grassTicket;

    @Getter
    @DatabaseField(canBeNull = false)
    private String itemId;

    @Getter
    @DatabaseField(canBeNull = false)
    private int count;

    @Setter
    @DatabaseField(foreign = true, canBeNull = false)
    private Farm farm;

    // O/R Mapperで必要
    public FarmGrass() {
    }

    public FarmGrass(long grassTicket, String itemId, int count) {
        this.grassTicket = grassTicket;
        this.itemId = itemId;
        this.count = count;
    }

    public static FarmGrass from(GrassList dto) {
        return new FarmGrass(dto.getGrassTicket(), dto.getItemId(), dto.getCount());
    }
}
