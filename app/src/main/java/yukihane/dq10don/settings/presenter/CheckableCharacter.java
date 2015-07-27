package yukihane.dq10don.settings.presenter;

import lombok.Getter;
import lombok.Setter;
import yukihane.dq10don.account.*;
import yukihane.dq10don.account.Character;

/**
 * Created by yuki on 15/07/28.
 */
public class CheckableCharacter {

    private final Character character;

    @Getter
    @Setter
    private boolean checked;

    public CheckableCharacter(Character character, boolean checked) {
        this.character = character;
        this.checked = checked;
    }

    public String getSmileUniqNo() {
        return character.getSmileUniqNo();
    }

    public String getCharacterName() {
        return character.getCharacterName();
    }

    public void toggleChecked() {
        checked = !checked;
    }
}
