
package yukihane.dq10don.communication.dto.tobatsu;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "area",
    "continentIconUrl",
    "count",
    "isSpecial",
    "monsterName",
    "point",
    "questNo",
    "questStatus",
    "tobatsuCrownUrl",
    "totalCount"
})
public class TobatsuList {

    @JsonProperty("area")
    private String area;
    @JsonProperty("continentIconUrl")
    private String continentIconUrl;
    @JsonProperty("count")
    private Integer count;
    @JsonProperty("isSpecial")
    private Boolean isSpecial;
    @JsonProperty("monsterName")
    private String monsterName;
    @JsonProperty("point")
    private Integer point;
    @JsonProperty("questNo")
    private Integer questNo;
    @JsonProperty("questStatus")
    private Integer questStatus;
    @JsonProperty("tobatsuCrownUrl")
    private Object tobatsuCrownUrl;
    @JsonProperty("totalCount")
    private Integer totalCount;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The area
     */
    @JsonProperty("area")
    public String getArea() {
        return area;
    }

    /**
     * 
     * @param area
     *     The area
     */
    @JsonProperty("area")
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * 
     * @return
     *     The continentIconUrl
     */
    @JsonProperty("continentIconUrl")
    public String getContinentIconUrl() {
        return continentIconUrl;
    }

    /**
     * 
     * @param continentIconUrl
     *     The continentIconUrl
     */
    @JsonProperty("continentIconUrl")
    public void setContinentIconUrl(String continentIconUrl) {
        this.continentIconUrl = continentIconUrl;
    }

    /**
     * 
     * @return
     *     The count
     */
    @JsonProperty("count")
    public Integer getCount() {
        return count;
    }

    /**
     * 
     * @param count
     *     The count
     */
    @JsonProperty("count")
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * 
     * @return
     *     The isSpecial
     */
    @JsonProperty("isSpecial")
    public Boolean getIsSpecial() {
        return isSpecial;
    }

    /**
     * 
     * @param isSpecial
     *     The isSpecial
     */
    @JsonProperty("isSpecial")
    public void setIsSpecial(Boolean isSpecial) {
        this.isSpecial = isSpecial;
    }

    /**
     * 
     * @return
     *     The monsterName
     */
    @JsonProperty("monsterName")
    public String getMonsterName() {
        return monsterName;
    }

    /**
     * 
     * @param monsterName
     *     The monsterName
     */
    @JsonProperty("monsterName")
    public void setMonsterName(String monsterName) {
        this.monsterName = monsterName;
    }

    /**
     * 
     * @return
     *     The point
     */
    @JsonProperty("point")
    public Integer getPoint() {
        return point;
    }

    /**
     * 
     * @param point
     *     The point
     */
    @JsonProperty("point")
    public void setPoint(Integer point) {
        this.point = point;
    }

    /**
     * 
     * @return
     *     The questNo
     */
    @JsonProperty("questNo")
    public Integer getQuestNo() {
        return questNo;
    }

    /**
     * 
     * @param questNo
     *     The questNo
     */
    @JsonProperty("questNo")
    public void setQuestNo(Integer questNo) {
        this.questNo = questNo;
    }

    /**
     * 
     * @return
     *     The questStatus
     */
    @JsonProperty("questStatus")
    public Integer getQuestStatus() {
        return questStatus;
    }

    /**
     * 
     * @param questStatus
     *     The questStatus
     */
    @JsonProperty("questStatus")
    public void setQuestStatus(Integer questStatus) {
        this.questStatus = questStatus;
    }

    /**
     * 
     * @return
     *     The tobatsuCrownUrl
     */
    @JsonProperty("tobatsuCrownUrl")
    public Object getTobatsuCrownUrl() {
        return tobatsuCrownUrl;
    }

    /**
     * 
     * @param tobatsuCrownUrl
     *     The tobatsuCrownUrl
     */
    @JsonProperty("tobatsuCrownUrl")
    public void setTobatsuCrownUrl(Object tobatsuCrownUrl) {
        this.tobatsuCrownUrl = tobatsuCrownUrl;
    }

    /**
     * 
     * @return
     *     The totalCount
     */
    @JsonProperty("totalCount")
    public Integer getTotalCount() {
        return totalCount;
    }

    /**
     * 
     * @param totalCount
     *     The totalCount
     */
    @JsonProperty("totalCount")
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
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
        return "TobatsuList{" +
                "area='" + area + '\'' +
                ", continentIconUrl='" + continentIconUrl + '\'' +
                ", count=" + count +
                ", isSpecial=" + isSpecial +
                ", monsterName='" + monsterName + '\'' +
                ", point=" + point +
                ", questNo=" + questNo +
                ", questStatus=" + questStatus +
                ", tobatsuCrownUrl=" + tobatsuCrownUrl +
                ", totalCount=" + totalCount +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
