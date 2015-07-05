package yukihane.dq10don.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuki on 15/07/05.
 */
public class LoginAccountDto {
    private int accountType;

    private List<CharacterDto> characterList = new ArrayList<>();

    private String cisuserid;
    private int resultCode;
    private String sessionId;
    private int slotSize;

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public List<CharacterDto> getCharacterList() {
        return characterList;
    }

    public void setCharacterList(List<CharacterDto> characterList) {
        this.characterList = characterList;
    }


    public String getCisuserid() {
        return cisuserid;
    }

    public void setCisuserid(String cisuserid) {
        this.cisuserid = cisuserid;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getSlotSize() {
        return slotSize;
    }

    public void setSlotSize(int slotSize) {
        this.slotSize = slotSize;
    }
}
