
package yukihane.dq10don.communication.dto.farm;

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
    "resultCode"
})
public class FarmLoginDto {

    @JsonProperty("resultCode")
    private Integer resultCode;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
