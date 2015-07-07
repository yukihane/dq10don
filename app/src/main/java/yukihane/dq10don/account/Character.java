package yukihane.dq10don.account;

import yukihane.dq10don.communication.dto.LoginCharacterDto;

/**
 * Created by yuki on 15/07/06.
 */
public class Character {
    private final String characterName;
    private final String smileUniqueNo;
    private final long webPcNo;
    private Tobatsu tobatsu;

    private Character(LoginCharacterDto dto) {
        characterName = dto.getCharacterName();
        smileUniqueNo = dto.getSmileUniqueNo();
        webPcNo = dto.getWebPcNo();
    }

    public static Character from(LoginCharacterDto dto) {
        return new Character(dto);
    }

    public Tobatsu getTobatsu() {
        return tobatsu;
    }

    public void setTobatsu(Tobatsu tobatsu) {
        this.tobatsu = tobatsu;
    }
}
