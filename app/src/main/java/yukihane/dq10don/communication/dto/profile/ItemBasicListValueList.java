
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
    "iconUrl",
    "isEquip",
    "isEquipForMonster",
    "isLock",
    "isNew",
    "isNotAttachable",
    "isNotTradable",
    "isProficient",
    "itemName",
    "itemQuality",
    "itemQualityMax",
    "itemUniqueNo",
    "itemUniqueNoStr",
    "renkinCount",
    "renkinList",
    "renkinListWithP",
    "reward",
    "stackCount",
    "targetMonster",
    "variousStr",
    "webItemId"
})
public class ItemBasicListValueList {

    @JsonProperty("iconUrl")
    private String iconUrl;
    @JsonProperty("isEquip")
    private Boolean isEquip;
    @JsonProperty("isEquipForMonster")
    private Boolean isEquipForMonster;
    @JsonProperty("isLock")
    private Boolean isLock;
    @JsonProperty("isNew")
    private Boolean isNew;
    @JsonProperty("isNotAttachable")
    private Boolean isNotAttachable;
    @JsonProperty("isNotTradable")
    private Boolean isNotTradable;
    @JsonProperty("isProficient")
    private Boolean isProficient;
    @JsonProperty("itemName")
    private String itemName;
    @JsonProperty("itemQuality")
    private Integer itemQuality;
    @JsonProperty("itemQualityMax")
    private Integer itemQualityMax;
    @JsonProperty("itemUniqueNo")
    private String itemUniqueNo;
    @JsonProperty("itemUniqueNoStr")
    private String itemUniqueNoStr;
    @JsonProperty("renkinCount")
    private Integer renkinCount;
    @JsonProperty("renkinList")
    private List<String> renkinList = new ArrayList<String>();
    @JsonProperty("renkinListWithP")
    private List<RenkinListWithP> renkinListWithP = new ArrayList<RenkinListWithP>();
    @JsonProperty("reward")
    private Object reward;
    @JsonProperty("stackCount")
    private Integer stackCount;
    @JsonProperty("targetMonster")
    private Object targetMonster;
    @JsonProperty("variousStr")
    private String variousStr;
    @JsonProperty("webItemId")
    private String webItemId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The iconUrl
     */
    @JsonProperty("iconUrl")
    public String getIconUrl() {
        return iconUrl;
    }

    /**
     * 
     * @param iconUrl
     *     The iconUrl
     */
    @JsonProperty("iconUrl")
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    /**
     * 
     * @return
     *     The isEquip
     */
    @JsonProperty("isEquip")
    public Boolean getIsEquip() {
        return isEquip;
    }

    /**
     * 
     * @param isEquip
     *     The isEquip
     */
    @JsonProperty("isEquip")
    public void setIsEquip(Boolean isEquip) {
        this.isEquip = isEquip;
    }

    /**
     * 
     * @return
     *     The isEquipForMonster
     */
    @JsonProperty("isEquipForMonster")
    public Boolean getIsEquipForMonster() {
        return isEquipForMonster;
    }

    /**
     * 
     * @param isEquipForMonster
     *     The isEquipForMonster
     */
    @JsonProperty("isEquipForMonster")
    public void setIsEquipForMonster(Boolean isEquipForMonster) {
        this.isEquipForMonster = isEquipForMonster;
    }

    /**
     * 
     * @return
     *     The isLock
     */
    @JsonProperty("isLock")
    public Boolean getIsLock() {
        return isLock;
    }

    /**
     * 
     * @param isLock
     *     The isLock
     */
    @JsonProperty("isLock")
    public void setIsLock(Boolean isLock) {
        this.isLock = isLock;
    }

    /**
     * 
     * @return
     *     The isNew
     */
    @JsonProperty("isNew")
    public Boolean getIsNew() {
        return isNew;
    }

    /**
     * 
     * @param isNew
     *     The isNew
     */
    @JsonProperty("isNew")
    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }

    /**
     * 
     * @return
     *     The isNotAttachable
     */
    @JsonProperty("isNotAttachable")
    public Boolean getIsNotAttachable() {
        return isNotAttachable;
    }

    /**
     * 
     * @param isNotAttachable
     *     The isNotAttachable
     */
    @JsonProperty("isNotAttachable")
    public void setIsNotAttachable(Boolean isNotAttachable) {
        this.isNotAttachable = isNotAttachable;
    }

    /**
     * 
     * @return
     *     The isNotTradable
     */
    @JsonProperty("isNotTradable")
    public Boolean getIsNotTradable() {
        return isNotTradable;
    }

    /**
     * 
     * @param isNotTradable
     *     The isNotTradable
     */
    @JsonProperty("isNotTradable")
    public void setIsNotTradable(Boolean isNotTradable) {
        this.isNotTradable = isNotTradable;
    }

    /**
     * 
     * @return
     *     The isProficient
     */
    @JsonProperty("isProficient")
    public Boolean getIsProficient() {
        return isProficient;
    }

    /**
     * 
     * @param isProficient
     *     The isProficient
     */
    @JsonProperty("isProficient")
    public void setIsProficient(Boolean isProficient) {
        this.isProficient = isProficient;
    }

    /**
     * 
     * @return
     *     The itemName
     */
    @JsonProperty("itemName")
    public String getItemName() {
        return itemName;
    }

    /**
     * 
     * @param itemName
     *     The itemName
     */
    @JsonProperty("itemName")
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 
     * @return
     *     The itemQuality
     */
    @JsonProperty("itemQuality")
    public Integer getItemQuality() {
        return itemQuality;
    }

    /**
     * 
     * @param itemQuality
     *     The itemQuality
     */
    @JsonProperty("itemQuality")
    public void setItemQuality(Integer itemQuality) {
        this.itemQuality = itemQuality;
    }

    /**
     * 
     * @return
     *     The itemQualityMax
     */
    @JsonProperty("itemQualityMax")
    public Integer getItemQualityMax() {
        return itemQualityMax;
    }

    /**
     * 
     * @param itemQualityMax
     *     The itemQualityMax
     */
    @JsonProperty("itemQualityMax")
    public void setItemQualityMax(Integer itemQualityMax) {
        this.itemQualityMax = itemQualityMax;
    }

    /**
     * 
     * @return
     *     The itemUniqueNo
     */
    @JsonProperty("itemUniqueNo")
    public String getItemUniqueNo() {
        return itemUniqueNo;
    }

    /**
     * 
     * @param itemUniqueNo
     *     The itemUniqueNo
     */
    @JsonProperty("itemUniqueNo")
    public void setItemUniqueNo(String itemUniqueNo) {
        this.itemUniqueNo = itemUniqueNo;
    }

    /**
     * 
     * @return
     *     The itemUniqueNoStr
     */
    @JsonProperty("itemUniqueNoStr")
    public String getItemUniqueNoStr() {
        return itemUniqueNoStr;
    }

    /**
     * 
     * @param itemUniqueNoStr
     *     The itemUniqueNoStr
     */
    @JsonProperty("itemUniqueNoStr")
    public void setItemUniqueNoStr(String itemUniqueNoStr) {
        this.itemUniqueNoStr = itemUniqueNoStr;
    }

    /**
     * 
     * @return
     *     The renkinCount
     */
    @JsonProperty("renkinCount")
    public Integer getRenkinCount() {
        return renkinCount;
    }

    /**
     * 
     * @param renkinCount
     *     The renkinCount
     */
    @JsonProperty("renkinCount")
    public void setRenkinCount(Integer renkinCount) {
        this.renkinCount = renkinCount;
    }

    /**
     * 
     * @return
     *     The renkinList
     */
    @JsonProperty("renkinList")
    public List<String> getRenkinList() {
        return renkinList;
    }

    /**
     * 
     * @param renkinList
     *     The renkinList
     */
    @JsonProperty("renkinList")
    public void setRenkinList(List<String> renkinList) {
        this.renkinList = renkinList;
    }

    /**
     * 
     * @return
     *     The renkinListWithP
     */
    @JsonProperty("renkinListWithP")
    public List<RenkinListWithP> getRenkinListWithP() {
        return renkinListWithP;
    }

    /**
     * 
     * @param renkinListWithP
     *     The renkinListWithP
     */
    @JsonProperty("renkinListWithP")
    public void setRenkinListWithP(List<RenkinListWithP> renkinListWithP) {
        this.renkinListWithP = renkinListWithP;
    }

    /**
     * 
     * @return
     *     The reward
     */
    @JsonProperty("reward")
    public Object getReward() {
        return reward;
    }

    /**
     * 
     * @param reward
     *     The reward
     */
    @JsonProperty("reward")
    public void setReward(Object reward) {
        this.reward = reward;
    }

    /**
     * 
     * @return
     *     The stackCount
     */
    @JsonProperty("stackCount")
    public Integer getStackCount() {
        return stackCount;
    }

    /**
     * 
     * @param stackCount
     *     The stackCount
     */
    @JsonProperty("stackCount")
    public void setStackCount(Integer stackCount) {
        this.stackCount = stackCount;
    }

    /**
     * 
     * @return
     *     The targetMonster
     */
    @JsonProperty("targetMonster")
    public Object getTargetMonster() {
        return targetMonster;
    }

    /**
     * 
     * @param targetMonster
     *     The targetMonster
     */
    @JsonProperty("targetMonster")
    public void setTargetMonster(Object targetMonster) {
        this.targetMonster = targetMonster;
    }

    /**
     * 
     * @return
     *     The variousStr
     */
    @JsonProperty("variousStr")
    public String getVariousStr() {
        return variousStr;
    }

    /**
     * 
     * @param variousStr
     *     The variousStr
     */
    @JsonProperty("variousStr")
    public void setVariousStr(String variousStr) {
        this.variousStr = variousStr;
    }

    /**
     * 
     * @return
     *     The webItemId
     */
    @JsonProperty("webItemId")
    public String getWebItemId() {
        return webItemId;
    }

    /**
     * 
     * @param webItemId
     *     The webItemId
     */
    @JsonProperty("webItemId")
    public void setWebItemId(String webItemId) {
        this.webItemId = webItemId;
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
