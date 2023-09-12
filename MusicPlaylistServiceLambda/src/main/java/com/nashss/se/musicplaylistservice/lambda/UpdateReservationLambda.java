package com.nashss.se.musicplaylistservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.musicplaylistservice.activity.requests.UpdateReservationRequest;
import com.nashss.se.musicplaylistservice.activity.results.UpdateReservationResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpdateReservationLambda
        extends LambdaActivityRunner<UpdateReservationRequest, UpdateReservationResult>
        implements RequestHandler<LambdaRequest<UpdateReservationRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();
    @Override
    public LambdaResponse handleRequest(LambdaRequest<UpdateReservationRequest> input, Context context) {
        log.info("handleRequest from Update Reservation Lambda");
        return super.runActivity(
                () -> input.fromPath(path ->
                        UpdateReservationRequest.builder()
                                .withReservationId(path.get("reservationId"))
                                .withPetOwnerId(path.get("petOwnerId"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideUpdateReservationActivity().handleRequest(request)
        );
    }
}
