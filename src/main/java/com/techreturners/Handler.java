package com.serverless;

import java.util.Collections;
import com.techreturners.model.Task;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class Handler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	private static final Logger LOG = LogManager.getLogger(Handler.class);

	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context){
		LOG.info("received: {}", input);

		Task t1 = new Task("12345", "this is task 1",false );
		Task t2 = new Task("67890", "this is task 2",false );
		//Task t3= null;
		//System.out.println(t3.getDescription());
		List<Task>tasks = new ArrayList<>();
		tasks.add(t1);	
		tasks.add(t2);	

		return ApiGatewayResponse.builder()
				.setStatusCode(200)
				.setObjectBody(tasks)
				.build();
	}
}
