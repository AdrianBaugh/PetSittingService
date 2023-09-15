package com.nashss.se.riverpetsittingservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.riverpetsittingservice.activity.requests.UpdateReservationRequest;
import com.nashss.se.riverpetsittingservice.activity.results.UpdateReservationResult;
import com.nashss.se.riverpetsittingservice.exceptions.ReservationException;
import com.nashss.se.riverpetsittingservice.exceptions.ReservationNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpdateReservationLambda
        extends LambdaActivityRunner<UpdateReservationRequest, UpdateReservationResult>
        implements RequestHandler<AuthenticatedLambdaRequest<UpdateReservationRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdateReservationRequest> input, Context context) {
        log.info("handleRequest from Get Reservation LAMBDA");
        return super.runActivity(
                () -> {
                    UpdateReservationRequest unauthenticatedRequest = input.fromPath(path ->
                            UpdateReservationRequest.builder()
                                    .withReservationId(path.get("reservationId"))
                                    .build());
                    UpdateReservationRequest bodyRequest = input.fromBody(UpdateReservationRequest.class);
                    return input.fromUserClaims(claims ->
                            UpdateReservationRequest.builder()
                                    .withReservationId(unauthenticatedRequest.getReservationId())
                                    .withPetOwnerId(claims.get("email"))
                                    .withStartDate(bodyRequest.getStartDate())
                                    .withEndDate(bodyRequest.getEndDate())
                                    .build());
                },

                (request, serviceComponent) ->
                    serviceComponent.provideUpdateReservationActivity().handleRequest(request)
        );
    }
}
