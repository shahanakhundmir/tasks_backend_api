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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;


public class GetRestaurantHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

	private static final Logger LOG = LogManager.getLogger(GetRestaurantHandler.class);

	@Override
	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context){
		LOG.info("received the request");

		int restId = parse.Int(request.getPathParameters().get("rest_id"));
		List<Restaurant>restaurants = new ArrayList<>();

		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        //String formatted1 = f.format("2001-01-01");
		Date d1 = f.parse("2001-01-01");
		
		//String formatted2 = f.format("2001-02-01");
		Date d2 = f.parse("2001-01-02");



		if (restId == 001){
			Restaurant r1 = new Restaurant(001, "this is rest 1","branchx", "blah", d1, d1, 002, "hhh", "ggg" );
			restaurants.add(r1);	

		}
		else{
			Restaurant r1 = new Restaurant(002, "this is rest 2","branchy", "blahx",d2, d2, 002, "hhhx", "gggx" );
			restaurants.add(r1);	
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
