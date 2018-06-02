package uber.com;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Edge{
    @JsonProperty("EdgeId")
    int edgeId;
    @JsonProperty("StartNodeId")
    int startNodeId;
    @JsonProperty("EndNodeId")
    int endNodeID;
    @JsonProperty("L2Distance")
    float l2Distance;
}
