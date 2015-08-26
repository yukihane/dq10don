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
    private String myNickname;

    @Getter
    @Setter
    private String ownerNickname;

    public TreasureboxTicket(long ticketNo, long webPcNo, String name) {
        this.treasureboxTicket = ticketNo;
        this.ownerWebpcno = webPcNo;
        this.myNickname = name;
        this.ownerNickname = name;
    }

    public static TreasureboxTicket from(FarmBox box, long webPcNo, String name) {
        return new TreasureboxTicket(box.getTicketNo(), webPcNo, name);
    }
}
