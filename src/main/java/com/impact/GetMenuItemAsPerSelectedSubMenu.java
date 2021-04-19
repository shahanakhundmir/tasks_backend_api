package com.impact;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.impact.model.MenuItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetMenuItemAsPerSelectedSubMenu implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
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
        String submenu = request.getPathParameters().get("submenu");
        List<MenuItem> menuItems = new ArrayList<>();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    String.format("jdbc:mysql://%s/%s?user=%s&password=%s",
                            System.getenv("DB_HOST"),
                            System.getenv("DB_NAME"),
                            System.getenv("DB_USER"),
                            System.getenv("DB_PASSWORD")));
            preparedStatement = connection.prepareStatement("select * from nandosdb.MenuItems where sub_menu = ?");
            preparedStatement.setString(1,submenu);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                MenuItem item = new MenuItem(
                        resultSet.getInt("item_id"),
                        resultSet.getString("name"),
                        resultSet.getString("image"),
                        resultSet.getString("short_desc"),
                        resultSet.getString("full_desc"),
                        resultSet.getString("factory_contam"),
                        resultSet.getString("kitchen_contam"),
                        resultSet.getString("ingredients"),
                        resultSet.getString("sub_menu"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("rest_id"),
                        resultSet.getDate("sys_creation_date"),
                        resultSet.getDate("sys_update_date"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("application_id"),
                        resultSet.getString("version_code"));
                menuItems.add(item);
            }
        }
        catch (Exception e){
            LOG.error(String.format("Unable to query database for Menuitem_Allergen"),e);
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
            String responseBody = objectMapper.writeValueAsString(menuItems);
            response.setBody(responseBody);
        }
        catch(JsonProcessingException e)
        {
            LOG.error("unable to marshall MenuItem Table", e);
        }
        return response;
    }
}