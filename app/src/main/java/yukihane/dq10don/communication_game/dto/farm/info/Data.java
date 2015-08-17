
package yukihane.dq10don.communication_game.dto.farm.info;

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
    "agencyInfo",
    "exp",
    "meatCount",
    "moreRebirthTreasureBox",
    "isBarnFull",
    "level",
    "decorationList",
    "isFriendBlueBox",
    "grassList",
    "roulette",
    "medal",
    "treasureboxList"
})
public class Data {

    @JsonProperty("agencyInfo")
    private AgencyInfo agencyInfo;
    @JsonProperty("exp")
    private Integer exp;
    @JsonProperty("meatCount")
    private Integer meatCount;
    @JsonProperty("moreRebirthTreasureBox")
    private Boolean moreRebirthTreasureBox;
    @JsonProperty("isBarnFull")
    private Boolean isBarnFull;
    @JsonProperty("level")
    private Integer level;
    @JsonProperty("decorationList")
    private List<DecorationList> decorationList = new ArrayList<DecorationList>();
    @JsonProperty("isFriendBlueBox")
    private Boolean isFriendBlueBox;
    @JsonProperty("grassList")
    private List<GrassList> grassList = new ArrayList<GrassList>();
    @JsonProperty("roulette")
    private Roulette roulette;
    @JsonProperty("medal")
    private Integer medal;
    @JsonProperty("treasureboxList")
    private List<TreasureboxList> treasureboxList = new ArrayList<TreasureboxList>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The agencyInfo
     */
    @JsonProperty("agencyInfo")
    public AgencyInfo getAgencyInfo() {
        return agencyInfo;
    }

    /**
     * 
     * @param agencyInfo
     *     The agencyInfo
     */
    @JsonProperty("agencyInfo")
    public void setAgencyInfo(AgencyInfo agencyInfo) {
        this.agencyInfo = agencyInfo;
    }

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
     *     The moreRebirthTreasureBox
     */
    @JsonProperty("moreRebirthTreasureBox")
    public Boolean getMoreRebirthTreasureBox() {
        return moreRebirthTreasureBox;
    }

    /**
     * 
     * @param moreRebirthTreasureBox
     *     The moreRebirthTreasureBox
     */
    @JsonProperty("moreRebirthTreasureBox")
    public void setMoreRebirthTreasureBox(Boolean moreRebirthTreasureBox) {
        this.moreRebirthTreasureBox = moreRebirthTreasureBox;
    }

    /**
     * 
     * @return
     *     The isBarnFull
     */
    @JsonProperty("isBarnFull")
    public Boolean getIsBarnFull() {
        return isBarnFull;
    }

    /**
     * 
     * @param isBarnFull
     *     The isBarnFull
     */
    @JsonProperty("isBarnFull")
    public void setIsBarnFull(Boolean isBarnFull) {
        this.isBarnFull = isBarnFull;
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
     *     The decorationList
     */
    @JsonProperty("decorationList")
    public List<DecorationList> getDecorationList() {
        return decorationList;
    }

    /**
     * 
     * @param decorationList
     *     The decorationList
     */
    @JsonProperty("decorationList")
    public void setDecorationList(List<DecorationList> decorationList) {
        this.decorationList = decorationList;
    }

    /**
     * 
     * @return
     *     The isFriendBlueBox
     */
    @JsonProperty("isFriendBlueBox")
    public Boolean getIsFriendBlueBox() {
        return isFriendBlueBox;
    }

    /**
     * 
     * @param isFriendBlueBox
     *     The isFriendBlueBox
     */
    @JsonProperty("isFriendBlueBox")
    public void setIsFriendBlueBox(Boolean isFriendBlueBox) {
        this.isFriendBlueBox = isFriendBlueBox;
    }

    /**
     * 
     * @return
     *     The grassList
     */
    @JsonProperty("grassList")
    public List<GrassList> getGrassList() {
        return grassList;
    }

    /**
     * 
     * @param grassList
     *     The grassList
     */
    @JsonProperty("grassList")
    public void setGrassList(List<GrassList> grassList) {
        this.grassList = grassList;
    }

    /**
     * 
     * @return
     *     The roulette
     */
    @JsonProperty("roulette")
    public Roulette getRoulette() {
        return roulette;
    }

    /**
     * 
     * @param roulette
     *     The roulette
     */
    @JsonProperty("roulette")
    public void setRoulette(Roulette roulette) {
        this.roulette = roulette;
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

    /**
     * 
     * @return
     *     The treasureboxList
     */
    @JsonProperty("treasureboxList")
    public List<TreasureboxList> getTreasureboxList() {
        return treasureboxList;
    }

    /**
     * 
     * @param treasureboxList
     *     The treasureboxList
     */
    @JsonProperty("treasureboxList")
    public void setTreasureboxList(List<TreasureboxList> treasureboxList) {
        this.treasureboxList = treasureboxList;
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
