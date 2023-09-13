package com.nashss.se.riverpetsittingservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.riverpetsittingservice.activity.requests.GetAllPetsRequest;
import com.nashss.se.riverpetsittingservice.activity.results.GetAllPetsResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetAllPetsLambda
        extends LambdaActivityRunner<GetAllPetsRequest, GetAllPetsResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetAllPetsRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetAllPetsRequest> input, Context context) {
        log.info("handleRequest from Get All Pets LAMBDA");
        return super.runActivity(
                () -> input.fromUserClaims(claims ->
                        GetAllPetsRequest.builder()
                                .withOwnerId(claims.get("email"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideGetAllPetsActivity().handleRequest(request)
        );
    }
}
