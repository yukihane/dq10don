package yukihane.dq10don.account;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class Account {

    private static final Logger LOGGER = LoggerFactory.getLogger(Account.class);

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

    public void setCharacters(List<Character> characters) {
        this.characters = new ArrayList<>(characters);
    }

    @Override
    public String toString() {
        return "Account{" +
                "sqexid='" + sqexid + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", characters=" + characters +
                '}';
    }
}
