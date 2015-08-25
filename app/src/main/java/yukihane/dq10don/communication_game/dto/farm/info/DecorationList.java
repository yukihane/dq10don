
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
    "seqno",
    "webpcno",
    "itemId",
    "positionX",
    "positionY",
    "status",
    "remainCount"
})
public class DecorationList {

    @JsonProperty("seqno")
    private Long seqno;
    @JsonProperty("webpcno")
    private Long webpcno;
    @JsonProperty("itemId")
    private String itemId;
    @JsonProperty("positionX")
    private Integer positionX;
    @JsonProperty("positionY")
    private Integer positionY;
    @JsonProperty("status")
    private String status;
    @JsonProperty("remainCount")
    private Integer remainCount;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The seqno
     */
    @JsonProperty("seqno")
    public Long getSeqno() {
        return seqno;
    }

    /**
     * 
     * @param seqno
     *     The seqno
     */
    @JsonProperty("seqno")
    public void setSeqno(Long seqno) {
        this.seqno = seqno;
    }

    /**
     * 
     * @return
     *     The webpcno
     */
    @JsonProperty("webpcno")
    public Long getWebpcno() {
        return webpcno;
    }

    /**
     * 
     * @param webpcno
     *     The webpcno
     */
    @JsonProperty("webpcno")
    public void setWebpcno(Long webpcno) {
        this.webpcno = webpcno;
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
     *     The positionX
     */
    @JsonProperty("positionX")
    public Integer getPositionX() {
        return positionX;
    }

    /**
     * 
     * @param positionX
     *     The positionX
     */
    @JsonProperty("positionX")
    public void setPositionX(Integer positionX) {
        this.positionX = positionX;
    }

    /**
     * 
     * @return
     *     The positionY
     */
    @JsonProperty("positionY")
    public Integer getPositionY() {
        return positionY;
    }

    /**
     * 
     * @param positionY
     *     The positionY
     */
    @JsonProperty("positionY")
    public void setPositionY(Integer positionY) {
        this.positionY = positionY;
    }

    /**
     * 
     * @return
     *     The status
     */
    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The remainCount
     */
    @JsonProperty("remainCount")
    public Integer getRemainCount() {
        return remainCount;
    }

    /**
     * 
     * @param remainCount
     *     The remainCount
     */
    @JsonProperty("remainCount")
    public void setRemainCount(Integer remainCount) {
        this.remainCount = remainCount;
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
