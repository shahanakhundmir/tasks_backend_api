package com.serverless;

import java.util.Collections;
import com.impact.model.Restaurant;
import com.impact.model.MenuItem;
import com.impact.model.Allergen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;


public class GetRestaurantHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

	private static final Logger LOG = LogManager.getLogger(GetTasksHandler.class);

	@Override
	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context){
		LOG.info("received the request");

		String userId = request.getPathParameters().get("userId");
		List<Task>tasks = new ArrayList<>();

		if (userId.equals("abcd123")){
			Task t1 = new Task("12345", "this is task 1",false );
			tasks.add(t1);	
		}
		else{
			Task t2 = new Task("67890", "this is task 2",false );
			tasks.add(t2);
		}
		
		APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
		response.setStatusCode(200);
		ObjectMapper objectMapper = new ObjectMapper();
		try{
			String responseBody = objectMapper.writeValueAsString(tasks);
			response.setBody(responseBody);
		}
		catch(JsonProcessingException e)
		{
			LOG.error("unable to marshall tasks array", e);
		}
		

		return response;
	}
}
