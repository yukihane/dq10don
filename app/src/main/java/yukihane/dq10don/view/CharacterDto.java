package yukihane.dq10don.view;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Getter;
import yukihane.dq10don.account.Account;
import yukihane.dq10don.account.Character;

/**
 * Created by yuki on 15/07/17.
 */
public class CharacterDto implements Parcelable {

    public static final Creator<CharacterDto> CREATOR = new Creator<CharacterDto>() {
        @Override
        public CharacterDto createFromParcel(Parcel in) {
            return new CharacterDto(in);
        }

        @Override
        public CharacterDto[] newArray(int size) {
            return new CharacterDto[size];
        }
    };

    @Getter
    private String sqexid;
    @Getter
    private String sessionId;
    @Getter
    private long webPcNo;
    @Getter
    private String smileUniqNo;
    @Getter
    private String characterName;

    public CharacterDto(String sqexid, String sessionId, long webPcNo, String smileUniqNo, String characterName) {
        this.sqexid = sqexid;
        this.sessionId = sessionId;
        this.webPcNo = webPcNo;
        this.smileUniqNo = smileUniqNo;
        this.characterName = characterName;
    }

    protected CharacterDto(Parcel in) {
        sqexid = in.readString();
        sessionId = in.readString();
        webPcNo = in.readLong();
        smileUniqNo = in.readString();
        characterName = in.readString();
    }

    public static CharacterDto from(Character c) {
        Account a = c.getAccount();
        return new CharacterDto(a.getSqexid(), a.getSessionId(),
                c.getWebPcNo(), c.getSmileUniqNo(), c.getCharacterName());
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshalled representation.
     *
     * @return a bitmask indicating the set of special object types marshalled
     * by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sqexid);
        dest.writeString(sessionId);
        dest.writeLong(webPcNo);
        dest.writeString(smileUniqNo);
        dest.writeString(characterName);
    }

    @Override
    public String toString() {
        return "CharacterDto{" +
                "sqexid='" + sqexid + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", webPcNo=" + webPcNo +
                ", smileUniqNo='" + smileUniqNo + '\'' +
                ", characterName='" + characterName + '\'' +
                '}';
    }
}
