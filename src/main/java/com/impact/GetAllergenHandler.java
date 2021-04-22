package com.impact;
import com.impact.model.Allergen;
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
public class GetAllergenHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private static final Logger LOG = LogManager.getLogger(GetAllergenHandler.class);
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
        List<Allergen> allergens = new ArrayList<>();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    String.format("jdbc:mysql://%s/%s?user=%s&password=%s",
                            System.getenv("DB_HOST"),
                            System.getenv("DB_NAME"),
                            System.getenv("DB_USER"),
                            System.getenv("DB_PASSWORD")));
            preparedStatement = connection.prepareStatement("select * from Allergens");
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Allergen allergen = new Allergen(
                        resultSet.getInt("allergen_id"),
                        resultSet.getString("allergen_name"),
                        resultSet.getString("allergen_image"),
                        resultSet.getDate("sys_creation_date"),
                        resultSet.getDate("sys_update_date"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("application_id"),
                        resultSet.getString("version_code"));
                allergens.add(allergen);
            }
        }
        catch (Exception e){
            LOG.error(String.format("Unable to query database for allergen"),e);
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
            String responseBody = objectMapper.writeValueAsString(allergens);
            response.setBody(responseBody);
        }
        catch(JsonProcessingException e)
        {
            LOG.error("unable to marshall Allergen table", e);
        }
        return response;
    }
}
