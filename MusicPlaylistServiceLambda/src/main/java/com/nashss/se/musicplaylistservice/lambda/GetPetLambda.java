package com.nashss.se.musicplaylistservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.logging.LogManager;
import java.util.logging.Logger;

public class GetPetLambda
        extends LambdaActivityRunner<GetPetRequest, GetPetResult>
        implements RequestHandler<LambdaRequest<GetPetRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetPetRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
                () -> input.fromPath(path ->
                        GetPetRequest.builder()
                                .withPetId(path.get("id"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideGetPetActivity().handleRequest(request)
        );
    }
}