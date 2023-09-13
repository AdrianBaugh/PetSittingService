package com.nashss.se.riverpetsittingservice.activity;

import com.nashss.se.riverpetsittingservice.activity.requests.CreatePetRequest;
import com.nashss.se.riverpetsittingservice.activity.results.CreatePetResult;
import com.nashss.se.riverpetsittingservice.converters.ModelConverter;
import com.nashss.se.riverpetsittingservice.dynamodb.PetDao;
import com.nashss.se.riverpetsittingservice.dynamodb.models.Pet;
import com.nashss.se.riverpetsittingservice.exceptions.InvalidAttributeValueException;
import com.nashss.se.riverpetsittingservice.models.PetModel;
import com.nashss.se.riverpetsittingservice.utils.IdUtils;

import javax.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreatePetActivity {
    private final Logger log = LogManager.getLogger();
    private final PetDao petDao;
    /**
     * Instantiates a new CreatePetActivity object.
     *
     * @param petDao PetDao to access the pets table.
     */
    @Inject
    public CreatePetActivity(PetDao petDao) {
        this.petDao = petDao;
    }

    /**
     * This method handles the incoming request by persisting a new pet
     * with the provided pet name and customer ID from the request.
     * <p>
     * It then returns the newly created pet with a generated petId.
     * <p>
     * If the provided pet name or customer ID has invalid characters, throws an
     * InvalidAttributeValueException
     *
     * @param createPetRequest request an object containing the pet name and pet ownerID
     *                              associated with it
     * @return createPetResult a result object containing the API defined {@link PetModel}
     */
    public CreatePetResult handleRequest(final CreatePetRequest createPetRequest) {
        log.info("received CreatePetRequest {}", createPetRequest);

        if (!IdUtils.isValidString(createPetRequest.getPetName())) {
            throw new InvalidAttributeValueException("Reservation name [" + createPetRequest.getPetName() +
                    "] contains illegal characters");
        }

        Pet newPet = new Pet();
        newPet.setPetId(IdUtils.generatePetId());
        newPet.setPetName(createPetRequest.getPetName());
        newPet.setOwnerId(createPetRequest.getOwnerId());
        newPet.setOwnerName(createPetRequest.getOwnerName());

        petDao.savePet(newPet);

        PetModel petModel = new ModelConverter().toPetModel(newPet);
        return CreatePetResult.builder()
                .withPet(petModel)
                .build();
    }
}
