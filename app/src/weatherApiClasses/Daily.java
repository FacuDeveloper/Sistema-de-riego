package weatherApiClasses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "data"
})
public class Daily {

  @JsonProperty("data")
  private List<Datum> data = null;

  @JsonProperty("data")
  public List<Datum> getData() {
    return data;
  }

  @JsonProperty("data")
  public void setData(List<Datum> data) {
    this.data = data;
  }

}
