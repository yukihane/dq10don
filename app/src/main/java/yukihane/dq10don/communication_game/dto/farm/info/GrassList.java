
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
    "grassTicket",
    "itemId",
    "count"
})
public class GrassList {

    @JsonProperty("grassTicket")
    private Long grassTicket;
    @JsonProperty("itemId")
    private String itemId;
    @JsonProperty("count")
    private Integer count;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The grassTicket
     */
    @JsonProperty("grassTicket")
    public Long getGrassTicket() {
        return grassTicket;
    }

    /**
     * 
     * @param grassTicket
     *     The grassTicket
     */
    @JsonProperty("grassTicket")
    public void setGrassTicket(Long grassTicket) {
        this.grassTicket = grassTicket;
    }

    /**
     * 
     * @return
     *     The itemId
     */
    @JsonProperty("itemId")
    public String getItemId() {
        return itemId;
    }

    /**
     * 
     * @param itemId
     *     The itemId
     */
    @JsonProperty("itemId")
    public void setItemId(String itemId) {
        this.itemId = itemId;
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

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
