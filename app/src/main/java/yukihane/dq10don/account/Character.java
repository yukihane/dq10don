package yukihane.dq10don.account;

import lombok.Getter;
import yukihane.dq10don.communication.dto.login.CharacterList;

/**
 * Created by yuki on 15/07/08.
 */
public class Character {

    @Getter
    private Account account;
    @Getter
    private String characterName;
    @Getter
    private long webPcNo;

    public static Character from(Account account, CharacterList dto) {
        Character obj = new Character();
        obj.account = account;
        obj.characterName = dto.getCharacterName();
        obj.webPcNo = dto.getWebPcNo();

        return obj;
    }
}
