package weatherApiClasses;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "latitude",
  "longitude",
  "timezone",
  "daily",
  "flags",
  "offset"
})
public class ForecastResponse {

  @JsonProperty("latitude")
  private Double latitude;

  @JsonProperty("longitude")
  private Double longitude;

  @JsonProperty("timezone")
  private String timezone;

  @JsonProperty("daily")
  private Daily daily;

  @JsonProperty("flags")
  private Flags flags;

  @JsonProperty("offset")
  private Integer offset;

  @JsonProperty("latitude")
  public Double getLatitude() {
    return latitude;
  }

  @JsonProperty("latitude")
  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  @JsonProperty("longitude")
  public Double getLongitude() {
    return longitude;
  }

  @JsonProperty("longitude")
  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  @JsonProperty("timezone")
  public String getTimezone() {
    return timezone;
  }

  @JsonProperty("timezone")
  public void setTimezone(String timezone) {
    this.timezone = timezone;
  }

  @JsonProperty("daily")
  public Daily getDaily() {
    return daily;
  }

  @JsonProperty("daily")
  public void setDaily(Daily daily) {
    this.daily = daily;
  }

  @JsonProperty("flags")
  public Flags getFlags() {
    return flags;
  }

  @JsonProperty("flags")
  public void setFlags(Flags flags) {
    this.flags = flags;
  }

  @JsonProperty("offset")
  public Integer getOffset() {
    return offset;
  }

  @JsonProperty("offset")
  public void setOffset(Integer offset) {
    this.offset = offset;
  }

}
