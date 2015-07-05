package yukihane.dq10don.utils;

/**
 * Created by yuki on 15/07/05.
 */
public class CharacterDto {
    private String characterName;
    private String iconUrl;
    private String job;
    private int jobId;
    private int lv;
    private int slotNo;
    private String smileUniqueNo;
    private long webPcNo;

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public int getLv() {
        return lv;
    }

    public void setLv(int lv) {
        this.lv = lv;
    }

    public int getSlotNo() {
        return slotNo;
    }

    public void setSlotNo(int slotNo) {
        this.slotNo = slotNo;
    }

    public String getSmileUniqueNo() {
        return smileUniqueNo;
    }

    public void setSmileUniqueNo(String smileUniqueNo) {
        this.smileUniqueNo = smileUniqueNo;
    }

    public long getWebPcNo() {
        return webPcNo;
    }

    public void setWebPcNo(long webPcNo) {
        this.webPcNo = webPcNo;
    }
}
