
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
    "continentName",
    "countySize",
    "issuedDate",
    "tobatsuList",
    "type"
})
public class TobatsuDataList {

    @JsonProperty("continentName")
    private String continentName;
    @JsonProperty("countySize")
    private Integer countySize;
    @JsonProperty("issuedDate")
    private String issuedDate;
    @JsonProperty("tobatsuList")
    private List<TobatsuList> tobatsuList = new ArrayList<TobatsuList>();
    @JsonProperty("type")
    private Integer type;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The continentName
     */
    @JsonProperty("continentName")
    public String getContinentName() {
        return continentName;
    }

    /**
     * 
     * @param continentName
     *     The continentName
     */
    @JsonProperty("continentName")
    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    /**
     * 
     * @return
     *     The countySize
     */
    @JsonProperty("countySize")
    public Integer getCountySize() {
        return countySize;
    }

    /**
     * 
     * @param countySize
     *     The countySize
     */
    @JsonProperty("countySize")
    public void setCountySize(Integer countySize) {
        this.countySize = countySize;
    }

    /**
     * 
     * @return
     *     The issuedDate
     */
    @JsonProperty("issuedDate")
    public String getIssuedDate() {
        return issuedDate;
    }

    /**
     * 
     * @param issuedDate
     *     The issuedDate
     */
    @JsonProperty("issuedDate")
    public void setIssuedDate(String issuedDate) {
        this.issuedDate = issuedDate;
    }

    /**
     * 
     * @return
     *     The tobatsuList
     */
    @JsonProperty("tobatsuList")
    public List<TobatsuList> getTobatsuList() {
        return tobatsuList;
    }

    /**
     * 
     * @param tobatsuList
     *     The tobatsuList
     */
    @JsonProperty("tobatsuList")
    public void setTobatsuList(List<TobatsuList> tobatsuList) {
        this.tobatsuList = tobatsuList;
    }

    /**
     * 
     * @return
     *     The type
     */
    @JsonProperty("type")
    public Integer getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    @JsonProperty("type")
    public void setType(Integer type) {
        this.type = type;
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
        return "TobatsuDataList{" +
                "continentName='" + continentName + '\'' +
                ", countySize=" + countySize +
                ", issuedDate='" + issuedDate + '\'' +
                ", tobatsuList=" + tobatsuList +
                ", type=" + type +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
