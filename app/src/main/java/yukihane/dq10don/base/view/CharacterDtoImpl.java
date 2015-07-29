package yukihane.dq10don.base.view;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Getter;
import yukihane.dq10don.account.Account;
import yukihane.dq10don.account.Character;
import yukihane.dq10don.base.presenter.CharacterDto;

/**
 * Created by yuki on 15/07/17.
 */
public class CharacterDtoImpl implements CharacterDto, Parcelable {

    public static final Creator<CharacterDtoImpl> CREATOR = new Creator<CharacterDtoImpl>() {
        @Override
        public CharacterDtoImpl createFromParcel(Parcel in) {
            return new CharacterDtoImpl(in);
        }

        @Override
        public CharacterDtoImpl[] newArray(int size) {
            return new CharacterDtoImpl[size];
        }
    };

    @Getter
    private String sqexid;
    @Getter
    private long webPcNo;
    @Getter
    private String smileUniqNo;
    @Getter
    private String characterName;

    public CharacterDtoImpl(String sqexid, long webPcNo, String smileUniqNo, String characterName) {
        this.sqexid = sqexid;
        this.webPcNo = webPcNo;
        this.smileUniqNo = smileUniqNo;
        this.characterName = characterName;
    }

    protected CharacterDtoImpl(Parcel in) {
        sqexid = in.readString();
        webPcNo = in.readLong();
        smileUniqNo = in.readString();
        characterName = in.readString();
    }

    public static CharacterDtoImpl from(Character c) {
        Account a = c.getAccount();
        return new CharacterDtoImpl(a.getSqexid(),
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
        dest.writeLong(webPcNo);
        dest.writeString(smileUniqNo);
        dest.writeString(characterName);
    }

    @Override
    public String toString() {
        return "CharacterDtoImpl{" +
                "sqexid='" + sqexid + '\'' +
                ", webPcNo=" + webPcNo +
                ", smileUniqNo='" + smileUniqNo + '\'' +
                ", characterName='" + characterName + '\'' +
                '}';
    }
}
