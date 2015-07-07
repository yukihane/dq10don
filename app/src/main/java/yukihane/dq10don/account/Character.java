package yukihane.dq10don.account;

import yukihane.dq10don.login.CharacterDto;

/**
 * Created by yuki on 15/07/06.
 */
public class Character {
    private final String characterName;
    private final String smileUniqueNo;
    private final long webPcNo;
    private Tobatsu tobatsu;

    private Character(CharacterDto dto) {
        characterName = dto.getCharacterName();
        smileUniqueNo = dto.getSmileUniqueNo();
        webPcNo = dto.getWebPcNo();
    }

    public static Character from(CharacterDto dto) {
        return new Character(dto);
    }

    public Tobatsu getTobatsu() {
        return tobatsu;
    }

    public void setTobatsu(Tobatsu tobatsu) {
        this.tobatsu = tobatsu;
    }
}
