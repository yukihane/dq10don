package yukihane.dq10don.communication.dto;

/**
 * Created by yuki on 15/07/08.
 */
public class CharaSelectDto {
    private String encWebPcNo;

    private int resultCode;

    public String getEncWebPcNo() {
        return encWebPcNo;
    }

    public void setEncWebPcNo(String encWebPcNo) {
        this.encWebPcNo = encWebPcNo;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    @Override
    public String toString() {
        return "CharaSelectDto{" +
                "encWebPcNo='" + encWebPcNo + '\'' +
                ", resultCode=" + resultCode +
                '}';
    }
}
