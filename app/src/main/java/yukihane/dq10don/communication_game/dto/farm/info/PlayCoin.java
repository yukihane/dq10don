
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
    "playCount",
    "coin"
})
public class PlayCoin {

    @JsonProperty("playCount")
    private Integer playCount;
    @JsonProperty("coin")
    private Integer coin;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
     *     The coin
     */
    @JsonProperty("coin")
    public Integer getCoin() {
        return coin;
    }

    /**
     * 
     * @param coin
     *     The coin
     */
    @JsonProperty("coin")
    public void setCoin(Integer coin) {
        this.coin = coin;
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
