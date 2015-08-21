package yukihane.dq10don.account;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import lombok.Getter;
import lombok.Setter;
import yukihane.dq10don.communication_game.dto.farm.info.TreasureboxList;

/**
 * モンスター牧場の宝箱.
 */
@DatabaseTable
public class FarmBox {

    @DatabaseField(generatedId = true, canBeNull = false)
    private Long id;

    @Getter
    @DatabaseField(canBeNull = false)
    private long ticketNo;

    @Getter
    @DatabaseField(canBeNull = false)
    private String expiredDt;

    @Getter
    @DatabaseField(canBeNull = false)
    private String type;

    @Setter
    @DatabaseField(foreign = true, canBeNull = false)
    private Farm farm;

    // O/R Mapper で必要
    public FarmBox() {
    }

    public FarmBox(long ticketNo, String expiredDt, String type) {
        this.ticketNo = ticketNo;
        this.expiredDt = expiredDt;
        this.type = type;
    }

    public static FarmBox from(TreasureboxList dto) {
        return new FarmBox(dto.getTicketNo(), dto.getExpiredDt(), dto.getType());
    }
}
