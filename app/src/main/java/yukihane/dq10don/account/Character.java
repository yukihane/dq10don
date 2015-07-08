package yukihane.dq10don.account;

import yukihane.dq10don.communication.dto.login.CharacterList;

/**
 * Created by yuki on 15/07/08.
 */
public class Character {

    private String characterName;

    public static Character from(CharacterList dto) {
        Character obj = new Character();
        obj.characterName = dto.getCharacterName();

        return obj;
    }

    public String getCharacterName() {
        return characterName;
    }
}
