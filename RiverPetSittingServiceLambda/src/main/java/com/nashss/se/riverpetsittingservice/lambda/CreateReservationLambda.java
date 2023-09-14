package com.nashss.se.riverpetsittingservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.riverpetsittingservice.activity.requests.CreateReservationRequest;
import com.nashss.se.riverpetsittingservice.activity.results.CreateReservationResult;
import com.nashss.se.riverpetsittingservice.exceptions.ReservationException;

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
                                    .withPetOwnerId(claims.get("email"))
                                    .build());
                },
                (request, serviceComponent) ->
                {
                    try {
                        return serviceComponent.provideCreateReservationActivity().handleRequest(request);
                    } catch (ReservationException e) {
                        throw new RuntimeException(e.getMessage());
                    }
                }
        );
    }
}
