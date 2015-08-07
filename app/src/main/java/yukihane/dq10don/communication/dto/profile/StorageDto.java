
package yukihane.dq10don.communication.dto.profile;

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
    "resultCode",
    "storageListValueList"
})
public class StorageDto {

    @JsonProperty("resultCode")
    private Integer resultCode;
    @JsonProperty("storageListValueList")
    private List<StorageListValueList> storageListValueList = new ArrayList<StorageListValueList>();
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

    /**
     * 
     * @return
     *     The storageListValueList
     */
    @JsonProperty("storageListValueList")
    public List<StorageListValueList> getStorageListValueList() {
        return storageListValueList;
    }

    /**
     * 
     * @param storageListValueList
     *     The storageListValueList
     */
    @JsonProperty("storageListValueList")
    public void setStorageListValueList(List<StorageListValueList> storageListValueList) {
        this.storageListValueList = storageListValueList;
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
