
package yukihane.dq10don.communication.dto.login;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "characterName",
    "iconUrl",
    "job",
    "jobId",
    "lv",
    "slotNo",
    "smileUniqueNo",
    "webPcNo"
})
public class CharacterList {

    @JsonProperty("characterName")
    private String characterName;
    @JsonProperty("iconUrl")
    private String iconUrl;
    @JsonProperty("job")
    private String job;
    @JsonProperty("jobId")
    private Integer jobId;
    @JsonProperty("lv")
    private Integer lv;
    @JsonProperty("slotNo")
    private Integer slotNo;
    @JsonProperty("smileUniqueNo")
    private String smileUniqueNo;
    @JsonProperty("webPcNo")
    private Integer webPcNo;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The characterName
     */
    @JsonProperty("characterName")
    public String getCharacterName() {
        return characterName;
    }

    /**
     * 
     * @param characterName
     *     The characterName
     */
    @JsonProperty("characterName")
    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    /**
     * 
     * @return
     *     The iconUrl
     */
    @JsonProperty("iconUrl")
    public String getIconUrl() {
        return iconUrl;
    }

    /**
     * 
     * @param iconUrl
     *     The iconUrl
     */
    @JsonProperty("iconUrl")
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    /**
     * 
     * @return
     *     The job
     */
    @JsonProperty("job")
    public String getJob() {
        return job;
    }

    /**
     * 
     * @param job
     *     The job
     */
    @JsonProperty("job")
    public void setJob(String job) {
        this.job = job;
    }

    /**
     * 
     * @return
     *     The jobId
     */
    @JsonProperty("jobId")
    public Integer getJobId() {
        return jobId;
    }

    /**
     * 
     * @param jobId
     *     The jobId
     */
    @JsonProperty("jobId")
    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    /**
     * 
     * @return
     *     The lv
     */
    @JsonProperty("lv")
    public Integer getLv() {
        return lv;
    }

    /**
     * 
     * @param lv
     *     The lv
     */
    @JsonProperty("lv")
    public void setLv(Integer lv) {
        this.lv = lv;
    }

    /**
     * 
     * @return
     *     The slotNo
     */
    @JsonProperty("slotNo")
    public Integer getSlotNo() {
        return slotNo;
    }

    /**
     * 
     * @param slotNo
     *     The slotNo
     */
    @JsonProperty("slotNo")
    public void setSlotNo(Integer slotNo) {
        this.slotNo = slotNo;
    }

    /**
     * 
     * @return
     *     The smileUniqueNo
     */
    @JsonProperty("smileUniqueNo")
    public String getSmileUniqueNo() {
        return smileUniqueNo;
    }

    /**
     * 
     * @param smileUniqueNo
     *     The smileUniqueNo
     */
    @JsonProperty("smileUniqueNo")
    public void setSmileUniqueNo(String smileUniqueNo) {
        this.smileUniqueNo = smileUniqueNo;
    }

    /**
     * 
     * @return
     *     The webPcNo
     */
    @JsonProperty("webPcNo")
    public Integer getWebPcNo() {
        return webPcNo;
    }

    /**
     * 
     * @param webPcNo
     *     The webPcNo
     */
    @JsonProperty("webPcNo")
    public void setWebPcNo(Integer webPcNo) {
        this.webPcNo = webPcNo;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
