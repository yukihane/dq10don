package yukihane.dq10don.account;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.Getter;
import yukihane.dq10don.communication.dto.login.CharacterList;
import yukihane.dq10don.communication.dto.login.LoginDto;

/**
 * スクエニアカウントを表現するクラス.
 */
public class Account {

    @Getter
    private String sqexid;

    @Getter
    private String sessionId;

    private List<Character> characters;

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
