
package yukihane.dq10don.communication_game.dto.farm.mowgrass;

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
    "exp",
    "meatCount",
    "level",
    "itemList",
    "medal"
})
public class Data {

    @JsonProperty("exp")
    private Integer exp;
    @JsonProperty("meatCount")
    private Integer meatCount;
    @JsonProperty("level")
    private Integer level;
    @JsonProperty("itemList")
    private List<ItemList> itemList = new ArrayList<ItemList>();
    @JsonProperty("medal")
    private Integer medal;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The exp
     */
    @JsonProperty("exp")
    public Integer getExp() {
        return exp;
    }

    /**
     * 
     * @param exp
     *     The exp
     */
    @JsonProperty("exp")
    public void setExp(Integer exp) {
        this.exp = exp;
    }

    /**
     * 
     * @return
     *     The meatCount
     */
    @JsonProperty("meatCount")
    public Integer getMeatCount() {
        return meatCount;
    }

    /**
     * 
     * @param meatCount
     *     The meatCount
     */
    @JsonProperty("meatCount")
    public void setMeatCount(Integer meatCount) {
        this.meatCount = meatCount;
    }

    /**
     * 
     * @return
     *     The level
     */
    @JsonProperty("level")
    public Integer getLevel() {
        return level;
    }

    /**
     * 
     * @param level
     *     The level
     */
    @JsonProperty("level")
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 
     * @return
     *     The itemList
     */
    @JsonProperty("itemList")
    public List<ItemList> getItemList() {
        return itemList;
    }

    /**
     * 
     * @param itemList
     *     The itemList
     */
    @JsonProperty("itemList")
    public void setItemList(List<ItemList> itemList) {
        this.itemList = itemList;
    }

    /**
     * 
     * @return
     *     The medal
     */
    @JsonProperty("medal")
    public Integer getMedal() {
        return medal;
    }

    /**
     * 
     * @param medal
     *     The medal
     */
    @JsonProperty("medal")
    public void setMedal(Integer medal) {
        this.medal = medal;
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
