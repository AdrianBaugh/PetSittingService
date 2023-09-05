package com.nashss.se.musicplaylistservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.musicplaylistservice.activity.requests.GetPetRequest;

import com.nashss.se.musicplaylistservice.activity.results.GetPetResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetPetLambda
        extends LambdaActivityRunner<GetPetRequest, GetPetResult>
        implements RequestHandler<LambdaRequest<GetPetRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetPetRequest> input, Context context) {
        log.info("handleRequest from Get Pet LAMBDA");
        return super.runActivity(
                () -> input.fromPath(path ->
                        GetPetRequest.builder()
                                .withPetId(path.get("petId"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideGetPetActivity().handleRequest(request)
        );
    }
}