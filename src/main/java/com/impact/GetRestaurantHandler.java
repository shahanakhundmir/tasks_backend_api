package com.impact;
import com.impact.model.Restaurant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
public class GetRestaurantHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
   private static final Logger LOG = LogManager.getLogger(GetRestaurantHandler.class);
   private Connection connection= null;
   private PreparedStatement preparedStatement=null;
   private ResultSet resultSet = null;
   private void closeConnection(){
      try{
         if(resultSet != null)
            resultSet.close();
         if(preparedStatement != null)
            preparedStatement.close();
         if(connection != null)
            connection.close();
      }
      catch (Exception e){
         LOG.error("Unable to close connection to MYSQL - {}",e.getMessage());
      }
   }
   @Override
   public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context){
      LOG.info("received the request");
      List<Restaurant>restaurants = new ArrayList<>();
      try{
         Class.forName("com.mysql.jdbc.Driver");
         connection = DriverManager.getConnection(
                 String.format("jdbc:mysql://%s/%s?user=%s&password=%s",
                         System.getenv("DB_HOST"),
                         System.getenv("DB_NAME"),
                         System.getenv("DB_USER"),
                         System.getenv("DB_PASSWORD")));
         preparedStatement = connection.prepareStatement("select * from Restaurant");
         resultSet = preparedStatement.executeQuery();
         while(resultSet.next()){
            Restaurant restaurant = new Restaurant(resultSet.getInt("rest_id"),
                    resultSet.getString("rest_name"),
                    resultSet.getString("rest_branch"),
                    resultSet.getString("allergen_safety"),
                    resultSet.getDate("sys_creation_date"),
                    resultSet.getDate("sys_update_date"),
                    resultSet.getInt("user_id"),
                    resultSet.getString("application_id"),
                    resultSet.getString("version_code"));
            restaurants.add(restaurant);
         }
      }
      catch (Exception e){
         LOG.error(String.format("Unable to query database for restaurant"),e);
      }
      finally{
         closeConnection();
      }
      APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
      response.setStatusCode(200);
      Map<String, String> headers = new HashMap<>();
      headers.put("Access-Control-Allow-Origin", "*");
      response.setHeaders(headers);
      ObjectMapper objectMapper = new ObjectMapper();
      try{
         String responseBody = objectMapper.writeValueAsString(restaurants);
         response.setBody(responseBody);
      }
      catch(JsonProcessingException e)
      {
         LOG.error("unable to marshall Restaurant table", e);
      }
      return response;
   }
}