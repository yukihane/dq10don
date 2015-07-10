package yukihane.dq10don.account;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import lombok.Getter;
import yukihane.dq10don.communication.dto.login.CharacterList;

/**
 * キャラクターを表現するクラス.
 */
@DatabaseTable
public class Character {

    @Getter
    @DatabaseField(id = true, canBeNull = false)
    private long webPcNo;

    @Getter
    @DatabaseField(foreign = true, canBeNull = false)
    private Account account;

    @Getter
    @DatabaseField(canBeNull = false)
    private String characterName;

    public static Character from(Account account, CharacterList dto) {
        Character obj = new Character();
        obj.account = account;
        obj.characterName = dto.getCharacterName();
        obj.webPcNo = dto.getWebPcNo();

        return obj;
    }
}
