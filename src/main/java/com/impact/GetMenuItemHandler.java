//MenuItem Handler
package com.impact;
import com.impact.model.MenuItem;
import java.util.ArrayList;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;


public class GetMenuItemHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

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

        List<MenuItem> menuItems = new ArrayList<>();

        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    String.format("jdbc:mysql://%s/%s?user=%s&password=%s",
                            System.getenv("DB_HOST"),
                            System.getenv("DB_NAME"),
                            System.getenv("DB_USER"),
                            System.getenv("DB_PASSWORD")));

            preparedStatement = connection.prepareStatement("select * from MenuItems");
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
                        resultSet.getDate("sys_creation_date"),
                        resultSet.getDate("sys_update_date"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("application_id"),
                        resultSet.getString("version_code"));

                menuItems.add(item);
            }
        }
        catch (Exception e){
            LOG.error(String.format("Unable to query database for menuitem"),e);
        }
        finally{
            closeConnection();
        }


        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        response.setStatusCode(200);
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