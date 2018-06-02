/*
 Author: Nouru Muneza

 This has been a worth while project
 Time Spent: 30+ hours


 First time project in Java
 */

package uber.com;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import java.io.IOException;
import java.util.Map;
import retrofit2.http.GET;

public class Main {

    interface RoadNetworkService {
        @GET("https://gist.githubusercontent.com/BenjaminMalley/9eadf45dbe11ba9c3ac34c45f905cfe8/raw/2c363711b601fa39a5d0071f10158b86217e530f/nodes.json")
        retrofit2.Call<Map<String, Node>> nodes();

        @GET("https://gist.github.com/BenjaminMalley/9eadf45dbe11ba9c3ac34c45f905cfe8/raw/2c363711b601fa39a5d0071f10158b86217e530f/edges.json")
        retrofit2.Call<Map<String, Edge>> edges();
    }

        private static void declarative()
        {
        RoadNetworkService service = new Retrofit
                .Builder()
                .baseUrl("https://gist.github.com/BenjaminMalley/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
                .create(RoadNetworkService.class);
        try {
            Response<Map<String, Node>> response = service.nodes().execute();

            Map<String, Node> nodes = null;
            if (response.isSuccessful()) {
                nodes = response.body();
            }
            System.out.println("\nList of Nodes:");

            for (Node node : nodes.values()) {
                System.out.println(node.NodeId);
            }

            Response<Map<String, Edge>> edgesResponse = service.edges().execute();
            Map<String, Edge> edges = null;

            if(edgesResponse.isSuccessful()){
                edges = edgesResponse.body();
            }
            System.out.println("\nList of Edges:");
            for(Edge edge : edges.values())
            {
                System.out.println((edge.edgeId));

            }
            try {

                Dijkstra.buildGraph(edges);//calling buildMap
            }  catch(Exception e) {e.printStackTrace();}

        }catch (IOException e){
            System.out.println(e.getLocalizedMessage());
        }

    }
    public static void main(String[] args) {
        declarative();
    }

}