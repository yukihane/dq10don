package yukihane.dq10don.account;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import yukihane.dq10don.communication.dto.login.CharacterList;
import yukihane.dq10don.communication.dto.login.LoginDto;

/**
 * スクエニアカウントを表現するクラス.
 */
public class Account {

    private String sqexid;
    private List<Character> characters;

    public static Account from(LoginDto dto, String sqexid) {
        Account obj = new Account();
        obj.sqexid = sqexid;
        obj.characters = new ArrayList<>(dto.getCharacterList().size());
        for(CharacterList cl : dto.getCharacterList()) {
            Character c = Character.from(cl);
            obj.characters.add(c);
        }
        return obj;
    }

    public String getSqexid() {
        return sqexid;
    }

    public Iterator<Character> getCharacters() {
        return characters.iterator();
    }
}
