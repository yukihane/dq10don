
package yukihane.dq10don.communication_game.dto.login;

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
    "farmToken"
})
public class Data {

    @JsonProperty("farmToken")
    private String farmToken;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The farmToken
     */
    @JsonProperty("farmToken")
    public String getFarmToken() {
        return farmToken;
    }

    /**
     * 
     * @param farmToken
     *     The farmToken
     */
    @JsonProperty("farmToken")
    public void setFarmToken(String farmToken) {
        this.farmToken = farmToken;
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
