package com.nashss.se.musicplaylistservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.nashss.se.musicplaylistservice.activity.requests.GetReservationRequest;

import com.nashss.se.musicplaylistservice.activity.results.GetReservationResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetReservationLambda
        extends LambdaActivityRunner<GetReservationRequest, GetReservationResult>
        implements RequestHandler<LambdaRequest<GetReservationRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetReservationRequest> input, Context context) {
        log.info("handleRequest from Get Reservation LAMBDA");
        return super.runActivity(
                () -> input.fromPath(path ->
                        GetReservationRequest.builder()
                                .withReservationId(path.get("reservationId"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideGetReservationActivity().handleRequest(request)
        );
    }
}
