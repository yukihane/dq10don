package yukihane.dq10don.account;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import yukihane.dq10don.communication.dto.login.CharacterList;
import yukihane.dq10don.communication.dto.login.LoginDto;

/**
 * スクエニアカウントを表現するクラス.
 */
@DatabaseTable
public class Account {

    @DatabaseField(id = true, canBeNull = false)
    @Getter
    private String sqexid;

    @DatabaseField
    @Getter
    private String sessionId;

    /**
     * サーバーにリクエストを送った際, 認証されていない場合などにtrueとする.
     * 次回以降の自動処理では, このフラグを見て予めエラーとなることがわかるため,
     * 処理をスキップすることができる.
     */
    @DatabaseField
    @Setter
    @Getter
    private boolean invalid;

    private List<Character> characters;

    // ORMライブラリで必要
    private Account() {
    }

    public Account(String sqexid, String sessionId, List<Character> characters) {
        this.sqexid = sqexid;
        this.sessionId = sessionId;
        setCharacters(characters);
    }

    public static Account from(LoginDto dto, String sqexid) {
        List<Character> characters = new ArrayList<>(dto.getCharacterList().size());
        for (CharacterList cl : dto.getCharacterList()) {
            Character c = Character.from(cl);
            characters.add(c);
        }
        return new Account(sqexid, dto.getSessionId(), characters);
    }

    public List<Character> getCharacters() {
        return new ArrayList<>(characters);
    }

    public void setCharacters(List<Character> characters) {
        this.characters = new ArrayList<>(characters);
        for (Character c : characters) {
            c.setAccount(this);
        }
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
