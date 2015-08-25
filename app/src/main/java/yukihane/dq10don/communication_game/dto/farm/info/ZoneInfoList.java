
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
    "zone",
    "weight",
    "name"
})
public class ZoneInfoList {

    @JsonProperty("zone")
    private String zone;
    @JsonProperty("weight")
    private Integer weight;
    @JsonProperty("name")
    private String name;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The zone
     */
    @JsonProperty("zone")
    public String getZone() {
        return zone;
    }

    /**
     * 
     * @param zone
     *     The zone
     */
    @JsonProperty("zone")
    public void setZone(String zone) {
        this.zone = zone;
    }

    /**
     * 
     * @return
     *     The weight
     */
    @JsonProperty("weight")
    public Integer getWeight() {
        return weight;
    }

    /**
     * 
     * @param weight
     *     The weight
     */
    @JsonProperty("weight")
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    /**
     * 
     * @return
     *     The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
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
