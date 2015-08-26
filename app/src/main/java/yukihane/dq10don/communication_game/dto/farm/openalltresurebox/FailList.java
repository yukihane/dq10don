
package yukihane.dq10don.communication_game.dto.farm.openalltresurebox;

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
    "errorCode",
    "type",
    "treasureboxTicket"
})
public class FailList {

    @JsonProperty("errorCode")
    private String errorCode;
    @JsonProperty("type")
    private String type;
    @JsonProperty("treasureboxTicket")
    private Long treasureboxTicket;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The errorCode
     */
    @JsonProperty("errorCode")
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * 
     * @param errorCode
     *     The errorCode
     */
    @JsonProperty("errorCode")
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * 
     * @return
     *     The type
     */
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The treasureboxTicket
     */
    @JsonProperty("treasureboxTicket")
    public Long getTreasureboxTicket() {
        return treasureboxTicket;
    }

    /**
     * 
     * @param treasureboxTicket
     *     The treasureboxTicket
     */
    @JsonProperty("treasureboxTicket")
    public void setTreasureboxTicket(Long treasureboxTicket) {
        this.treasureboxTicket = treasureboxTicket;
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
        return "FailList{" +
                "treasureboxTicket=" + treasureboxTicket +
                ", type='" + type + '\'' +
                ", errorCode='" + errorCode + '\'' +
                '}';
    }
}
