package com.nashss.se.musicplaylistservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.musicplaylistservice.activity.requests.CreateReservationRequest;
import com.nashss.se.musicplaylistservice.activity.results.CreateReservationResult;

public class CreateReservationLambda
        extends LambdaActivityRunner<CreateReservationRequest, CreateReservationResult>
        implements RequestHandler<AuthenticatedLambdaRequest<CreateReservationRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateReservationRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    CreateReservationRequest unauthenticatedRequest = input.fromBody(CreateReservationRequest.class);
                    return input.fromUserClaims(claims ->
                            CreateReservationRequest.builder()
                                    .withStartDate(unauthenticatedRequest.getStartDate())
                                    .withEndDate(unauthenticatedRequest.getEndDate())
                                    .withPetList(unauthenticatedRequest.getPetList())
                                    .withSitterId(unauthenticatedRequest.getSitterId())
                                    .withPetOwnerId(claims.get("email"))
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideCreateReservationActivity().handleRequest(request)
        );
    }
}
