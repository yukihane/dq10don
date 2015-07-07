package yukihane.dq10don.account;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import yukihane.dq10don.communication.dto.CharacterDto;
import yukihane.dq10don.login.LoginAccountDto;

/**
 * Created by yuki on 15/07/06.
 */
public class Account {

    private final String sessionId;

    private List<Character> characters;

    private Account(LoginAccountDto dto) {
        this.sessionId = dto.getSessionId();
        this.characters = new ArrayList<>(dto.getCharacterList().size());

        for(CharacterDto cdto : dto.getCharacterList()){
            characters.add(Character.from(cdto));
        }
    }

    public static Account from(LoginAccountDto dto) {
        return new Account(dto);
    }

    public String getSessionId() {
        return sessionId;
    }

    public Iterator<Character> getCharacters() {
        return characters.iterator();
    }
}
