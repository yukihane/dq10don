
package yukihane.dq10don.communication.dto;

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
    "encWebPcNo",
    "resultCode",
    "supportKey"
})
public class CharaSelectDto {

    @JsonProperty("encWebPcNo")
    private String encWebPcNo;
    @JsonProperty("resultCode")
    private Integer resultCode;
    @JsonProperty("supportKey")
    private String supportKey;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The encWebPcNo
     */
    @JsonProperty("encWebPcNo")
    public String getEncWebPcNo() {
        return encWebPcNo;
    }

    /**
     * 
     * @param encWebPcNo
     *     The encWebPcNo
     */
    @JsonProperty("encWebPcNo")
    public void setEncWebPcNo(String encWebPcNo) {
        this.encWebPcNo = encWebPcNo;
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
     *     The supportKey
     */
    @JsonProperty("supportKey")
    public String getSupportKey() {
        return supportKey;
    }

    /**
     * 
     * @param supportKey
     *     The supportKey
     */
    @JsonProperty("supportKey")
    public void setSupportKey(String supportKey) {
        this.supportKey = supportKey;
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
