package com.nashss.se.riverpetsittingservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.riverpetsittingservice.activity.requests.CreatePetRequest;
import com.nashss.se.riverpetsittingservice.activity.results.CreatePetResult;


public class CreatePetLambda
        extends LambdaActivityRunner<CreatePetRequest, CreatePetResult>
        implements RequestHandler<AuthenticatedLambdaRequest<CreatePetRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreatePetRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    CreatePetRequest unauthenticatedRequest = input.fromBody(CreatePetRequest.class);
                    return input.fromUserClaims(claims ->
                            CreatePetRequest.builder()
                                    .withPetName(unauthenticatedRequest.getPetName())
                                    .withOwnerId(claims.get("email"))
                                    .withOwnerName(claims.get("name"))
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideCreatePetActivity().handleRequest(request)
        );
    }
}
