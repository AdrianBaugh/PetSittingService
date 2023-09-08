package com.nashss.se.musicplaylistservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.musicplaylistservice.activity.requests.GetAllReservationsRequest;
import com.nashss.se.musicplaylistservice.activity.results.GetAllReservationsResult;

public class GetAllReservationsLambda
        extends LambdaActivityRunner<GetAllReservationsRequest, GetAllReservationsResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetAllReservationsRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetAllReservationsRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    return input.fromUserClaims(claims ->
                            GetAllReservationsRequest.builder()
                                    .withPetOwnerId(claims.get("email"))
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideGetAllReservationsActivity().handleRequest(request)
        );
    }
}
