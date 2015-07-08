package yukihane.dq10don.account;

import yukihane.dq10don.communication.dto.login.CharacterList;

/**
 * Created by yuki on 15/07/08.
 */
public class Character {

    private Account account;
    private String characterName;
    private long webPcNo;

    public static Character from(Account account, CharacterList dto) {
        Character obj = new Character();
        obj.account = account;
        obj.characterName = dto.getCharacterName();
        obj.webPcNo = dto.getWebPcNo();

        return obj;
    }

    public String getCharacterName() {
        return characterName;
    }

    public Account getAccount() {
        return account;
    }

    public long getWebPcNo() {
        return webPcNo;
    }
}
