package com.nashss.se.musicplaylistservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.musicplaylistservice.activity.requests.CancelReservationRequest;
import com.nashss.se.musicplaylistservice.activity.results.CancelReservationResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CancelReservationLambda
        extends LambdaActivityRunner<CancelReservationRequest, CancelReservationResult>
        implements RequestHandler<LambdaRequest<CancelReservationRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<CancelReservationRequest> input, Context context) {
        log.info("handleRequest from Cancel Reservation LAMBDA");
        return super.runActivity(
                () -> input.fromPath(path ->
                        CancelReservationRequest.builder()
                                .withReservationId(path.get("reservationId"))
                                .withOwnerId(path.get("ownerId"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideCancelReservationActivity().handleRequest(request)
        );
    }
}

