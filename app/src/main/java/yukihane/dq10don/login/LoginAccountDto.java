package yukihane.dq10don.login;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import yukihane.dq10don.communication.dto.LoginCharacterDto;

/**
 * Created by yuki on 15/07/05.
 */
public class LoginAccountDto {
    private int accountType;

    private List<LoginCharacterDto> characterList = new ArrayList<>();

    private String cisuserid;
    private int resultCode;
    private String sessionId;
    private int slotSize;

    public static LoginAccountDto fromJson(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, LoginAccountDto.class);
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public List<LoginCharacterDto> getCharacterList() {
        return characterList;
    }

    public void setCharacterList(List<LoginCharacterDto> characterList) {
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
