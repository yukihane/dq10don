
package yukihane.dq10don.communication.dto.profile;

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
    "isParupunte",
    "renkinName",
    "renkinNameType",
    "renkinWord"
})
public class RenkinListWithP {

    @JsonProperty("isParupunte")
    private Boolean isParupunte;
    @JsonProperty("renkinName")
    private String renkinName;
    @JsonProperty("renkinNameType")
    private Integer renkinNameType;
    @JsonProperty("renkinWord")
    private String renkinWord;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The isParupunte
     */
    @JsonProperty("isParupunte")
    public Boolean getIsParupunte() {
        return isParupunte;
    }

    /**
     * 
     * @param isParupunte
     *     The isParupunte
     */
    @JsonProperty("isParupunte")
    public void setIsParupunte(Boolean isParupunte) {
        this.isParupunte = isParupunte;
    }

    /**
     * 
     * @return
     *     The renkinName
     */
    @JsonProperty("renkinName")
    public String getRenkinName() {
        return renkinName;
    }

    /**
     * 
     * @param renkinName
     *     The renkinName
     */
    @JsonProperty("renkinName")
    public void setRenkinName(String renkinName) {
        this.renkinName = renkinName;
    }

    /**
     * 
     * @return
     *     The renkinNameType
     */
    @JsonProperty("renkinNameType")
    public Integer getRenkinNameType() {
        return renkinNameType;
    }

    /**
     * 
     * @param renkinNameType
     *     The renkinNameType
     */
    @JsonProperty("renkinNameType")
    public void setRenkinNameType(Integer renkinNameType) {
        this.renkinNameType = renkinNameType;
    }

    /**
     * 
     * @return
     *     The renkinWord
     */
    @JsonProperty("renkinWord")
    public String getRenkinWord() {
        return renkinWord;
    }

    /**
     * 
     * @param renkinWord
     *     The renkinWord
     */
    @JsonProperty("renkinWord")
    public void setRenkinWord(String renkinWord) {
        this.renkinWord = renkinWord;
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
