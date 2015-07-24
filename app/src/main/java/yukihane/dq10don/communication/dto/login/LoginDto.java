
package yukihane.dq10don.communication.dto.login;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "accountType",
    "characterList",
    "cisuserid",
    "resultCode",
    "sessionId",
    "slotSize"
})
public class LoginDto {

    @JsonProperty("accountType")
    private Integer accountType;
    @JsonProperty("characterList")
    private List<CharacterList> characterList = new ArrayList<CharacterList>();
    @JsonProperty("cisuserid")
    private String cisuserid;
    @JsonProperty("resultCode")
    private Integer resultCode;
    @JsonProperty("sessionId")
    private String sessionId;
    @JsonProperty("slotSize")
    private Integer slotSize;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The accountType
     */
    @JsonProperty("accountType")
    public Integer getAccountType() {
        return accountType;
    }

    /**
     * 
     * @param accountType
     *     The accountType
     */
    @JsonProperty("accountType")
    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    /**
     * 
     * @return
     *     The characterList
     */
    @JsonProperty("characterList")
    public List<CharacterList> getCharacterList() {
        return characterList;
    }

    /**
     * 
     * @param characterList
     *     The characterList
     */
    @JsonProperty("characterList")
    public void setCharacterList(List<CharacterList> characterList) {
        this.characterList = characterList;
    }

    /**
     * 
     * @return
     *     The cisuserid
     */
    @JsonProperty("cisuserid")
    public String getCisuserid() {
        return cisuserid;
    }

    /**
     * 
     * @param cisuserid
     *     The cisuserid
     */
    @JsonProperty("cisuserid")
    public void setCisuserid(String cisuserid) {
        this.cisuserid = cisuserid;
    }

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

    /**
     * 
     * @return
     *     The sessionId
     */
    @JsonProperty("sessionId")
    public String getSessionId() {
        return sessionId;
    }

    /**
     * 
     * @param sessionId
     *     The sessionId
     */
    @JsonProperty("sessionId")
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * 
     * @return
     *     The slotSize
     */
    @JsonProperty("slotSize")
    public Integer getSlotSize() {
        return slotSize;
    }

    /**
     * 
     * @param slotSize
     *     The slotSize
     */
    @JsonProperty("slotSize")
    public void setSlotSize(Integer slotSize) {
        this.slotSize = slotSize;
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
        return "LoginDto{" +
                "accountType=" + accountType +
                ", characterList=" + characterList +
                ", cisuserid='" + cisuserid + '\'' +
                ", resultCode=" + resultCode +
                ", sessionId='" + sessionId + '\'' +
                ", slotSize=" + slotSize +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
