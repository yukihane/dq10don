package yukihane.dq10don.account;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import lombok.Getter;
import lombok.Setter;
import yukihane.dq10don.communication.dto.login.CharacterList;

/**
 * キャラクターを表現するクラス.
 */
@DatabaseTable
public class Character {

    @Getter
    @DatabaseField(id = true, canBeNull = false)
    private long webPcNo;

    @DatabaseField(canBeNull = false)
    private String smileUniqNo;

    @Getter
    @Setter
    @DatabaseField(foreign = true, canBeNull = false)
    private Account account;

    @Getter
    @DatabaseField(canBeNull = false)
    private String characterName;

    // ORMライブラリで必要
    private Character() {
    }

    public Character(String characterName, String smileUniqueNo, Long webPcNo) {
        this.characterName = characterName;
        this.smileUniqNo = smileUniqueNo;
        this.webPcNo = webPcNo;
    }

    public static Character from(CharacterList dto) {
        return new Character(dto.getCharacterName(), dto.getSmileUniqueNo(), dto.getWebPcNo());
    }

    @Override
    public String toString() {
        return "Character{" +
                "webPcNo=" + webPcNo +
                ", smileUniqNo='" + smileUniqNo + '\'' +
                ", account=" + (account == null ? null : account.getSqexid()) +
                ", characterName='" + characterName + '\'' +
                '}';
    }
}
