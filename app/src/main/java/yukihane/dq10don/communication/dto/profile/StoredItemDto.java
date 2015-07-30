
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
    "itemBasicListValueList",
    "resultCode"
})
public class StoredItemDto {

    @JsonProperty("itemBasicListValueList")
    private List<ItemBasicListValueList> itemBasicListValueList = new ArrayList<ItemBasicListValueList>();
    @JsonProperty("resultCode")
    private Integer resultCode;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The itemBasicListValueList
     */
    @JsonProperty("itemBasicListValueList")
    public List<ItemBasicListValueList> getItemBasicListValueList() {
        return itemBasicListValueList;
    }

    /**
     * 
     * @param itemBasicListValueList
     *     The itemBasicListValueList
     */
    @JsonProperty("itemBasicListValueList")
    public void setItemBasicListValueList(List<ItemBasicListValueList> itemBasicListValueList) {
        this.itemBasicListValueList = itemBasicListValueList;
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

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
