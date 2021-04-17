package com.serverless;

import java.util.Collections;
import com.impact.model.Restaurant;
import com.impact.model.MenuItem;
import com.impact.model.Allergen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.*;
import java.text.*;
//import java.sql.Date;



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

		//String restId = request.getPathParameters().get("restId");
		List<Restaurant>restaurants = new ArrayList<>();

		try{
			Class.forName("com.mysql.jdbc.Driver");
   			connection = DriverManager.getConnection(
         		String.format("jdbc:mysql//%s/%s?user=%s&password=%s","3306",
				 "nandosinstance.cyvsgwhk3npl.eu-west-2.rds.amazonaws.com", "nandosdb", "root", "xxx"));
				 preparedStatement = connection.prepareStatement("select * from restaurant");
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
		ObjectMapper objectMapper = new ObjectMapper();
		try{
			String responseBody = objectMapper.writeValueAsString(restaurants);
			response.setBody(responseBody);
		}
		catch(JsonProcessingException e)
		{
			LOG.error("unable to marshall tasks array", e);
		}
		
		return response;
	}
}


/**try{
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = f.format( new Date());
			Date date = f.parse ( "2021-01-31" );   

			//if ( Integer.parseInt(restId) == 001){
			Restaurant r1 = new Restaurant(001, "this is rest 1","branchx", "blah", date, date, 002, "hhh", "ggg" );
			restaurants.add(r1);	
			Restaurant r2 = new Restaurant(002, "this is rest 2","branchy", "blahx",date, date, 002, "hhhx", "gggx" );
			restaurants.add(r2);	
			//}
			//else{
				
//}
		}
		catch(ParseException e)
		{LOG.error(e);}*/