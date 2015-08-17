
package yukihane.dq10don.communication_game.dto.farm.info;

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
    "agencyStatus",
    "freeRentalshipCount",
    "nearLimitDt",
    "questEntryCount",
    "nextSailoutDt",
    "ungetCount"
})
public class AgencyInfo {

    @JsonProperty("agencyStatus")
    private String agencyStatus;
    @JsonProperty("freeRentalshipCount")
    private Integer freeRentalshipCount;
    @JsonProperty("nearLimitDt")
    private String nearLimitDt;
    @JsonProperty("questEntryCount")
    private Integer questEntryCount;
    @JsonProperty("nextSailoutDt")
    private String nextSailoutDt;
    @JsonProperty("ungetCount")
    private Integer ungetCount;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The agencyStatus
     */
    @JsonProperty("agencyStatus")
    public String getAgencyStatus() {
        return agencyStatus;
    }

    /**
     * 
     * @param agencyStatus
     *     The agencyStatus
     */
    @JsonProperty("agencyStatus")
    public void setAgencyStatus(String agencyStatus) {
        this.agencyStatus = agencyStatus;
    }

    /**
     * 
     * @return
     *     The freeRentalshipCount
     */
    @JsonProperty("freeRentalshipCount")
    public Integer getFreeRentalshipCount() {
        return freeRentalshipCount;
    }

    /**
     * 
     * @param freeRentalshipCount
     *     The freeRentalshipCount
     */
    @JsonProperty("freeRentalshipCount")
    public void setFreeRentalshipCount(Integer freeRentalshipCount) {
        this.freeRentalshipCount = freeRentalshipCount;
    }

    /**
     * 
     * @return
     *     The nearLimitDt
     */
    @JsonProperty("nearLimitDt")
    public String getNearLimitDt() {
        return nearLimitDt;
    }

    /**
     * 
     * @param nearLimitDt
     *     The nearLimitDt
     */
    @JsonProperty("nearLimitDt")
    public void setNearLimitDt(String nearLimitDt) {
        this.nearLimitDt = nearLimitDt;
    }

    /**
     * 
     * @return
     *     The questEntryCount
     */
    @JsonProperty("questEntryCount")
    public Integer getQuestEntryCount() {
        return questEntryCount;
    }

    /**
     * 
     * @param questEntryCount
     *     The questEntryCount
     */
    @JsonProperty("questEntryCount")
    public void setQuestEntryCount(Integer questEntryCount) {
        this.questEntryCount = questEntryCount;
    }

    /**
     * 
     * @return
     *     The nextSailoutDt
     */
    @JsonProperty("nextSailoutDt")
    public String getNextSailoutDt() {
        return nextSailoutDt;
    }

    /**
     * 
     * @param nextSailoutDt
     *     The nextSailoutDt
     */
    @JsonProperty("nextSailoutDt")
    public void setNextSailoutDt(String nextSailoutDt) {
        this.nextSailoutDt = nextSailoutDt;
    }

    /**
     * 
     * @return
     *     The ungetCount
     */
    @JsonProperty("ungetCount")
    public Integer getUngetCount() {
        return ungetCount;
    }

    /**
     * 
     * @param ungetCount
     *     The ungetCount
     */
    @JsonProperty("ungetCount")
    public void setUngetCount(Integer ungetCount) {
        this.ungetCount = ungetCount;
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
