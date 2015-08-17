
package yukihane.dq10don.communication_game.dto.farm.info;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    "zoneInfoList",
    "playCoin",
    "playCount",
    "isEffectFinished"
})
public class Roulette {

    @JsonProperty("zoneInfoList")
    private List<ZoneInfoList> zoneInfoList = new ArrayList<ZoneInfoList>();
    @JsonProperty("playCoin")
    private List<PlayCoin> playCoin = new ArrayList<PlayCoin>();
    @JsonProperty("playCount")
    private Integer playCount;
    @JsonProperty("isEffectFinished")
    private Boolean isEffectFinished;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The zoneInfoList
     */
    @JsonProperty("zoneInfoList")
    public List<ZoneInfoList> getZoneInfoList() {
        return zoneInfoList;
    }

    /**
     * 
     * @param zoneInfoList
     *     The zoneInfoList
     */
    @JsonProperty("zoneInfoList")
    public void setZoneInfoList(List<ZoneInfoList> zoneInfoList) {
        this.zoneInfoList = zoneInfoList;
    }

    /**
     * 
     * @return
     *     The playCoin
     */
    @JsonProperty("playCoin")
    public List<PlayCoin> getPlayCoin() {
        return playCoin;
    }

    /**
     * 
     * @param playCoin
     *     The playCoin
     */
    @JsonProperty("playCoin")
    public void setPlayCoin(List<PlayCoin> playCoin) {
        this.playCoin = playCoin;
    }

    /**
     * 
     * @return
     *     The playCount
     */
    @JsonProperty("playCount")
    public Integer getPlayCount() {
        return playCount;
    }

    /**
     * 
     * @param playCount
     *     The playCount
     */
    @JsonProperty("playCount")
    public void setPlayCount(Integer playCount) {
        this.playCount = playCount;
    }

    /**
     * 
     * @return
     *     The isEffectFinished
     */
    @JsonProperty("isEffectFinished")
    public Boolean getIsEffectFinished() {
        return isEffectFinished;
    }

    /**
     * 
     * @param isEffectFinished
     *     The isEffectFinished
     */
    @JsonProperty("isEffectFinished")
    public void setIsEffectFinished(Boolean isEffectFinished) {
        this.isEffectFinished = isEffectFinished;
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
