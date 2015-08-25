package yukihane.dq10don.communication_game;

import lombok.Getter;
import lombok.Setter;
import yukihane.dq10don.account.FarmBox;

/**
 * Created by yuki on 15/08/25.
 */
public class TreasureboxTicket {

    @Getter
    @Setter
    private long treasureboxTicket;

    @Getter
    @Setter
    private long ownerWebpcno;

    @Getter
    @Setter
    private String myNickName;

    @Getter
    @Setter
    private String ownerNickName;

    public TreasureboxTicket(long ticketNo, long webPcNo, String name) {
        this.treasureboxTicket = ticketNo;
        this.ownerWebpcno = webPcNo;
        this.myNickName = name;
        this.ownerNickName = name;
    }

    public static TreasureboxTicket from(FarmBox box, long webPcNo, String name) {
        return new TreasureboxTicket(box.getTicketNo(), webPcNo, name);
    }
}
