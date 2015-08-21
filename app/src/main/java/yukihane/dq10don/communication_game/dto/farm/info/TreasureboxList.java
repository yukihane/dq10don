
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
    "ticketNo",
    "expiredDt",
    "type"
})
public class TreasureboxList {

    @JsonProperty("ticketNo")
    private Long ticketNo;
    @JsonProperty("expiredDt")
    private String expiredDt;
    @JsonProperty("type")
    private String type;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The ticketNo
     */
    @JsonProperty("ticketNo")
    public Long getTicketNo() {
        return ticketNo;
    }

    /**
     * 
     * @param ticketNo
     *     The ticketNo
     */
    @JsonProperty("ticketNo")
    public void setTicketNo(Long ticketNo) {
        this.ticketNo = ticketNo;
    }

    /**
     * 
     * @return
     *     The expiredDt
     */
    @JsonProperty("expiredDt")
    public String getExpiredDt() {
        return expiredDt;
    }

    /**
     * 
     * @param expiredDt
     *     The expiredDt
     */
    @JsonProperty("expiredDt")
    public void setExpiredDt(String expiredDt) {
        this.expiredDt = expiredDt;
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

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
