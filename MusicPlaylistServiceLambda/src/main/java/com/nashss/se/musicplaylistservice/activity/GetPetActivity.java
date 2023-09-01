package com.nashss.se.musicplaylistservice.activity;

import com.nashss.se.musicplaylistservice.activity.requests.GetPetRequest;
import com.nashss.se.musicplaylistservice.activity.results.GetPetResult;
import com.nashss.se.musicplaylistservice.converters.ModelConverter;
import com.nashss.se.musicplaylistservice.dynamodb.PetDao;
import com.nashss.se.musicplaylistservice.dynamodb.models.Pet;

import com.nashss.se.musicplaylistservice.exceptions.PetIdNotFoundException;
import com.nashss.se.musicplaylistservice.models.PetModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

    /**
     * Implementation of the GetPetActivity for the MusicPetService's GetPet API.
     *
     * This API allows the customer to get one of their saved pets.
     */
    public class GetPetActivity {
        private final Logger log = LogManager.getLogger();
        private final PetDao petDao;

        /**
         * Instantiates a new GetPetActivity object.
         *
         * @param petDao PetDao to access the pet table.
         */
        @Inject
        public GetPetActivity(PetDao petDao) {
            this.petDao = petDao;
        }

        /**
         * This method handles the incoming request by retrieving the pet from the database.
         * <p>
         * It then returns the pet.
         * <p>
         * If the pet does not exist, this should throw a PeNotFoundException.
         *
         * @param getPetRequest request object containing the pet ID
         * @return getPetResult result object containing the API defined {@link PetModel}
         */

        public GetPetResult handleRequest(final GetPetRequest getPetRequest) throws PetIdNotFoundException {
            log.info("Received GetPetRequest {}", getPetRequest);
            String requestedPetId = getPetRequest.getPetId();

            // Retrieve the pet from the database using the petDao
            Pet pet = petDao.getPetById(requestedPetId);

            if(pet == null){
                throw new PetIdNotFoundException("Pet with id "+ requestedPetId + " not found");
            }

            // Convert the retrieved Pet object into a PetModel
            PetModel petModel = new ModelConverter().toPetModel(pet);

            return GetPetResult.builder()
                    .withPet(petModel)
                    .build();
        }

    }




