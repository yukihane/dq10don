
package yukihane.dq10don.communication.dto.tobatsu;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "acceptedTobatsuDataList",
    "countryTobatsuDataList",
    "dailyClearCount",
    "isRealRendasia",
    "isTermDailyFlag",
    "isTermWeeklyFlag",
    "isTermWeeklyRealFlag",
    "rendasiaName",
    "resultCode",
    "weeklyClearCount",
    "weeklyRealClearCount"
})
public class TobatsuDto {

    @JsonProperty("acceptedTobatsuDataList")
    private List<TobatsuDataList> acceptedTobatsuDataList = new ArrayList<TobatsuDataList>();
    @JsonProperty("countryTobatsuDataList")
    private List<TobatsuDataList> countryTobatsuDataList = new ArrayList<TobatsuDataList>();
    @JsonProperty("dailyClearCount")
    private Integer dailyClearCount;
    @JsonProperty("isRealRendasia")
    private Boolean isRealRendasia;
    @JsonProperty("isTermDailyFlag")
    private Boolean isTermDailyFlag;
    @JsonProperty("isTermWeeklyFlag")
    private Boolean isTermWeeklyFlag;
    @JsonProperty("isTermWeeklyRealFlag")
    private Boolean isTermWeeklyRealFlag;
    @JsonProperty("rendasiaName")
    private String rendasiaName;
    @JsonProperty("resultCode")
    private Integer resultCode;
    @JsonProperty("weeklyClearCount")
    private Integer weeklyClearCount;
    @JsonProperty("weeklyRealClearCount")
    private Integer weeklyRealClearCount;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The acceptedTobatsuDataList
     */
    @JsonProperty("acceptedTobatsuDataList")
    public List<TobatsuDataList> getAcceptedTobatsuDataList() {
        return acceptedTobatsuDataList;
    }

    /**
     * 
     * @param acceptedTobatsuDataList
     *     The acceptedTobatsuDataList
     */
    @JsonProperty("acceptedTobatsuDataList")
    public void setAcceptedTobatsuDataList(List<TobatsuDataList> acceptedTobatsuDataList) {
        this.acceptedTobatsuDataList = acceptedTobatsuDataList;
    }

    /**
     * 
     * @return
     *     The countryTobatsuDataList
     */
    @JsonProperty("countryTobatsuDataList")
    public List<TobatsuDataList> getCountryTobatsuDataList() {
        return countryTobatsuDataList;
    }

    /**
     * 
     * @param countryTobatsuDataList
     *     The countryTobatsuDataList
     */
    @JsonProperty("countryTobatsuDataList")
    public void setCountryTobatsuDataList(List<TobatsuDataList> countryTobatsuDataList) {
        this.countryTobatsuDataList = countryTobatsuDataList;
    }

    /**
     * 
     * @return
     *     The dailyClearCount
     */
    @JsonProperty("dailyClearCount")
    public Integer getDailyClearCount() {
        return dailyClearCount;
    }

    /**
     * 
     * @param dailyClearCount
     *     The dailyClearCount
     */
    @JsonProperty("dailyClearCount")
    public void setDailyClearCount(Integer dailyClearCount) {
        this.dailyClearCount = dailyClearCount;
    }

    /**
     * 
     * @return
     *     The isRealRendasia
     */
    @JsonProperty("isRealRendasia")
    public Boolean getIsRealRendasia() {
        return isRealRendasia;
    }

    /**
     * 
     * @param isRealRendasia
     *     The isRealRendasia
     */
    @JsonProperty("isRealRendasia")
    public void setIsRealRendasia(Boolean isRealRendasia) {
        this.isRealRendasia = isRealRendasia;
    }

    /**
     * 
     * @return
     *     The isTermDailyFlag
     */
    @JsonProperty("isTermDailyFlag")
    public Boolean getIsTermDailyFlag() {
        return isTermDailyFlag;
    }

    /**
     * 
     * @param isTermDailyFlag
     *     The isTermDailyFlag
     */
    @JsonProperty("isTermDailyFlag")
    public void setIsTermDailyFlag(Boolean isTermDailyFlag) {
        this.isTermDailyFlag = isTermDailyFlag;
    }

    /**
     * 
     * @return
     *     The isTermWeeklyFlag
     */
    @JsonProperty("isTermWeeklyFlag")
    public Boolean getIsTermWeeklyFlag() {
        return isTermWeeklyFlag;
    }

    /**
     * 
     * @param isTermWeeklyFlag
     *     The isTermWeeklyFlag
     */
    @JsonProperty("isTermWeeklyFlag")
    public void setIsTermWeeklyFlag(Boolean isTermWeeklyFlag) {
        this.isTermWeeklyFlag = isTermWeeklyFlag;
    }

    /**
     * 
     * @return
     *     The isTermWeeklyRealFlag
     */
    @JsonProperty("isTermWeeklyRealFlag")
    public Boolean getIsTermWeeklyRealFlag() {
        return isTermWeeklyRealFlag;
    }

    /**
     * 
     * @param isTermWeeklyRealFlag
     *     The isTermWeeklyRealFlag
     */
    @JsonProperty("isTermWeeklyRealFlag")
    public void setIsTermWeeklyRealFlag(Boolean isTermWeeklyRealFlag) {
        this.isTermWeeklyRealFlag = isTermWeeklyRealFlag;
    }

    /**
     * 
     * @return
     *     The rendasiaName
     */
    @JsonProperty("rendasiaName")
    public String getRendasiaName() {
        return rendasiaName;
    }

    /**
     * 
     * @param rendasiaName
     *     The rendasiaName
     */
    @JsonProperty("rendasiaName")
    public void setRendasiaName(String rendasiaName) {
        this.rendasiaName = rendasiaName;
    }

    /**
     * 
     * @return
     *     The resultCode
     */
    @JsonProperty("resultCode")
    public Integer getResultCode() {
        return resultCode;
    }

    /**
     * 
     * @param resultCode
     *     The resultCode
     */
    @JsonProperty("resultCode")
    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    /**
     * 
     * @return
     *     The weeklyClearCount
     */
    @JsonProperty("weeklyClearCount")
    public Integer getWeeklyClearCount() {
        return weeklyClearCount;
    }

    /**
     * 
     * @param weeklyClearCount
     *     The weeklyClearCount
     */
    @JsonProperty("weeklyClearCount")
    public void setWeeklyClearCount(Integer weeklyClearCount) {
        this.weeklyClearCount = weeklyClearCount;
    }

    /**
     * 
     * @return
     *     The weeklyRealClearCount
     */
    @JsonProperty("weeklyRealClearCount")
    public Integer getWeeklyRealClearCount() {
        return weeklyRealClearCount;
    }

    /**
     * 
     * @param weeklyRealClearCount
     *     The weeklyRealClearCount
     */
    @JsonProperty("weeklyRealClearCount")
    public void setWeeklyRealClearCount(Integer weeklyRealClearCount) {
        this.weeklyRealClearCount = weeklyRealClearCount;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "TobatsuDto{" +
                "acceptedTobatsuDataList=" + acceptedTobatsuDataList +
                ", countryTobatsuDataList=" + countryTobatsuDataList +
                ", dailyClearCount=" + dailyClearCount +
                ", isRealRendasia=" + isRealRendasia +
                ", isTermDailyFlag=" + isTermDailyFlag +
                ", isTermWeeklyFlag=" + isTermWeeklyFlag +
                ", isTermWeeklyRealFlag=" + isTermWeeklyRealFlag +
                ", rendasiaName='" + rendasiaName + '\'' +
                ", resultCode=" + resultCode +
                ", weeklyClearCount=" + weeklyClearCount +
                ", weeklyRealClearCount=" + weeklyRealClearCount +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
