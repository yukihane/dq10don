
package yukihane.dq10don.communication_game.dto.farm.openalltresurebox;

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
    "successList",
    "failList"
})
public class Data {

    @JsonProperty("successList")
    private List<SuccessList> successList = new ArrayList<SuccessList>();
    @JsonProperty("failList")
    private List<FailList> failList = new ArrayList<FailList>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The successList
     */
    @JsonProperty("successList")
    public List<SuccessList> getSuccessList() {
        return successList;
    }

    /**
     * 
     * @param successList
     *     The successList
     */
    @JsonProperty("successList")
    public void setSuccessList(List<SuccessList> successList) {
        this.successList = successList;
    }

    /**
     * 
     * @return
     *     The failList
     */
    @JsonProperty("failList")
    public List<FailList> getFailList() {
        return failList;
    }

    /**
     * 
     * @param failList
     *     The failList
     */
    @JsonProperty("failList")
    public void setFailList(List<FailList> failList) {
        this.failList = failList;
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
