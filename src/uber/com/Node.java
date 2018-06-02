package uber.com;

import com.fasterxml.jackson.annotation.JsonProperty;

class Node{
    @JsonProperty("NodeID")
    int NodeId;
    @JsonProperty("Latitude")
    float Latitude;
    @JsonProperty("Longitude")
    float Longitude;
}
