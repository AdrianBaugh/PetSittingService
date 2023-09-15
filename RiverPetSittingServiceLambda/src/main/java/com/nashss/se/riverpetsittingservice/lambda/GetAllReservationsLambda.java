package com.nashss.se.riverpetsittingservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.riverpetsittingservice.activity.requests.GetAllReservationsRequest;
import com.nashss.se.riverpetsittingservice.activity.results.GetAllReservationsResult;
import com.nashss.se.riverpetsittingservice.exceptions.ReservationNotFoundException;

public class GetAllReservationsLambda
        extends LambdaActivityRunner<GetAllReservationsRequest, GetAllReservationsResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetAllReservationsRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetAllReservationsRequest> input, Context context) {
        return super.runActivity(
                () -> input.fromUserClaims(claims ->
                        GetAllReservationsRequest.builder()
                                .withPetOwnerId(claims.get("email"))
                                .build()),
                (request, serviceComponent) ->
                 serviceComponent.provideGetAllReservationsActivity().handleRequest(request)
        );
    }
}
