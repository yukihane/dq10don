package yukihane.dq10don.account;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.Getter;
import lombok.ToString;
import yukihane.dq10don.communication.dto.login.CharacterList;
import yukihane.dq10don.communication.dto.login.LoginDto;

/**
 * スクエニアカウントを表現するクラス.
 */
@DatabaseTable
@ToString(exclude = {"characters"})
public class Account {

    @DatabaseField(id = true, canBeNull = false)
    @Getter
    private String sqexid;

    @DatabaseField
    @Getter
    private String sessionId;

    private List<Character> characters;

    private Account() {
    }

    public static Account from(LoginDto dto, String sqexid) {
        Account obj = new Account();
        obj.sqexid = sqexid;
        obj.sessionId = dto.getSessionId();
        obj.characters = new ArrayList<>(dto.getCharacterList().size());
        for (CharacterList cl : dto.getCharacterList()) {
            Character c = Character.from(obj, cl);
            obj.characters.add(c);
        }
        return obj;
    }

    public Iterator<Character> getCharacters() {
        return characters.iterator();
    }
}
