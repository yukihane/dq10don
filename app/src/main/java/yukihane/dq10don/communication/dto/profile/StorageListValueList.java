
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
    "bagTypeNoFlag",
    "bagTypeNoFlagForDisplay",
    "canShare",
    "hasOnlySimpleInfo",
    "storageId",
    "storageIndex",
    "storageMaxSize",
    "storageName",
    "storageUse"
})
public class StorageListValueList {

    @JsonProperty("bagTypeNoFlag")
    private Integer bagTypeNoFlag;
    @JsonProperty("bagTypeNoFlagForDisplay")
    private Integer bagTypeNoFlagForDisplay;
    @JsonProperty("canShare")
    private Boolean canShare;
    @JsonProperty("hasOnlySimpleInfo")
    private Boolean hasOnlySimpleInfo;
    @JsonProperty("storageId")
    private Integer storageId;
    @JsonProperty("storageIndex")
    private Integer storageIndex;
    @JsonProperty("storageMaxSize")
    private Integer storageMaxSize;
    @JsonProperty("storageName")
    private String storageName;
    @JsonProperty("storageUse")
    private Integer storageUse;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The bagTypeNoFlag
     */
    @JsonProperty("bagTypeNoFlag")
    public Integer getBagTypeNoFlag() {
        return bagTypeNoFlag;
    }

    /**
     * 
     * @param bagTypeNoFlag
     *     The bagTypeNoFlag
     */
    @JsonProperty("bagTypeNoFlag")
    public void setBagTypeNoFlag(Integer bagTypeNoFlag) {
        this.bagTypeNoFlag = bagTypeNoFlag;
    }

    /**
     * 
     * @return
     *     The bagTypeNoFlagForDisplay
     */
    @JsonProperty("bagTypeNoFlagForDisplay")
    public Integer getBagTypeNoFlagForDisplay() {
        return bagTypeNoFlagForDisplay;
    }

    /**
     * 
     * @param bagTypeNoFlagForDisplay
     *     The bagTypeNoFlagForDisplay
     */
    @JsonProperty("bagTypeNoFlagForDisplay")
    public void setBagTypeNoFlagForDisplay(Integer bagTypeNoFlagForDisplay) {
        this.bagTypeNoFlagForDisplay = bagTypeNoFlagForDisplay;
    }

    /**
     * 
     * @return
     *     The canShare
     */
    @JsonProperty("canShare")
    public Boolean getCanShare() {
        return canShare;
    }

    /**
     * 
     * @param canShare
     *     The canShare
     */
    @JsonProperty("canShare")
    public void setCanShare(Boolean canShare) {
        this.canShare = canShare;
    }

    /**
     * 
     * @return
     *     The hasOnlySimpleInfo
     */
    @JsonProperty("hasOnlySimpleInfo")
    public Boolean getHasOnlySimpleInfo() {
        return hasOnlySimpleInfo;
    }

    /**
     * 
     * @param hasOnlySimpleInfo
     *     The hasOnlySimpleInfo
     */
    @JsonProperty("hasOnlySimpleInfo")
    public void setHasOnlySimpleInfo(Boolean hasOnlySimpleInfo) {
        this.hasOnlySimpleInfo = hasOnlySimpleInfo;
    }

    /**
     * 
     * @return
     *     The storageId
     */
    @JsonProperty("storageId")
    public Integer getStorageId() {
        return storageId;
    }

    /**
     * 
     * @param storageId
     *     The storageId
     */
    @JsonProperty("storageId")
    public void setStorageId(Integer storageId) {
        this.storageId = storageId;
    }

    /**
     * 
     * @return
     *     The storageIndex
     */
    @JsonProperty("storageIndex")
    public Integer getStorageIndex() {
        return storageIndex;
    }

    /**
     * 
     * @param storageIndex
     *     The storageIndex
     */
    @JsonProperty("storageIndex")
    public void setStorageIndex(Integer storageIndex) {
        this.storageIndex = storageIndex;
    }

    /**
     * 
     * @return
     *     The storageMaxSize
     */
    @JsonProperty("storageMaxSize")
    public Integer getStorageMaxSize() {
        return storageMaxSize;
    }

    /**
     * 
     * @param storageMaxSize
     *     The storageMaxSize
     */
    @JsonProperty("storageMaxSize")
    public void setStorageMaxSize(Integer storageMaxSize) {
        this.storageMaxSize = storageMaxSize;
    }

    /**
     * 
     * @return
     *     The storageName
     */
    @JsonProperty("storageName")
    public String getStorageName() {
        return storageName;
    }

    /**
     * 
     * @param storageName
     *     The storageName
     */
    @JsonProperty("storageName")
    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }

    /**
     * 
     * @return
     *     The storageUse
     */
    @JsonProperty("storageUse")
    public Integer getStorageUse() {
        return storageUse;
    }

    /**
     * 
     * @param storageUse
     *     The storageUse
     */
    @JsonProperty("storageUse")
    public void setStorageUse(Integer storageUse) {
        this.storageUse = storageUse;
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
