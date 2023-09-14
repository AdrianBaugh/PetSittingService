package com.nashss.se.riverpetsittingservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.nashss.se.riverpetsittingservice.activity.requests.CancelReservationRequest;
import com.nashss.se.riverpetsittingservice.activity.results.CancelReservationResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CancelReservationLambda
        extends LambdaActivityRunner<CancelReservationRequest, CancelReservationResult>
        implements RequestHandler<AuthenticatedLambdaRequest<CancelReservationRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CancelReservationRequest> input, Context context) {
        log.info("handleRequest from Cancel Reservation LAMBDA");
        return super.runActivity(
                () -> {
                    CancelReservationRequest unauthenticatedRequest = input.fromPath(path ->
                            CancelReservationRequest.builder()
                                    .withReservationId(path.get("reservationId"))
                                    .build());

                    return input.fromUserClaims(claims ->
                            CancelReservationRequest.builder()
                                    .withReservationId(unauthenticatedRequest.getReservationId())
                                    .withOwnerId(claims.get("email"))
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideCancelReservationActivity().handleRequest(request)
        );
    }
}

