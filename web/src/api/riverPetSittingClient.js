import axios from "axios";
import BindingClass from "../util/bindingClass";
import Authenticator from "./authenticator";

/**
 * Client to call the RiverPetSittingService.
 *
 * This could be a great place to explore Mixins. Currently the client is being loaded multiple times on each page,
 * which we could avoid using inheritance or Mixins.
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Classes#Mix-ins
 * https://javascript.info/mixins
  */
export default class RiverPetSittingClient extends BindingClass {

    constructor(props = {}) {
        super();
            //Add Methods after 'logout' when we implement them.
        const methodsToBind = ['clientLoaded', 'getIdentity', 'login', 'logout', 'createPet', 'viewPet', 'viewAllPets', 'viewReservation', 'viewAllReservations', 'cancelReservation'];
        this.bindClassMethods(methodsToBind, this);

        this.authenticator = new Authenticator();;
        this.props = props;

        axios.defaults.baseURL = process.env.API_BASE_URL;
        this.axiosClient = axios;
        this.clientLoaded();
    }

    /**
     * Run any functions that are supposed to be called once the client has loaded successfully.
     */
    clientLoaded() {
        if (this.props.hasOwnProperty("onReady")) {
            this.props.onReady(this);
        }
    }

    /**
     * Get the identity of the current user
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The user information for the current user.
     */
    async getIdentity(errorCallback) {
        try {
            const isLoggedIn = await this.authenticator.isUserLoggedIn();

            if (!isLoggedIn) {
                return undefined;
            }

            return await this.authenticator.getCurrentUserInfo();
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async login() {
        this.authenticator.login();
    }

    async logout() {
        this.authenticator.logout();
    }

    async getTokenOrThrow(unauthenticatedErrorMessage) {
        const isLoggedIn = await this.authenticator.isUserLoggedIn();
        if (!isLoggedIn) {
            throw new Error(unauthenticatedErrorMessage);
        }

        return await this.authenticator.getUserToken();
    }

    // /**
    //  * Create a new pet owned by the current user.
    //  * @param petName The name of the pet to create
    //  * @param errorCallback (Optional) A function to execute if the call fails.
    //  * @returns The pet that has been created.
    //  */
    async createPet(petName, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can add new pets.");
            const response = await this.axiosClient.post(`pets`, {
                petName: petName,
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.pet;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
    * Create a new reservation by the current user.
    * @param startDate The start date of the reservation to create.
    * @param endDate The end date of the reservation to be created.
    * @param pets Metadata pets to associate with a reservation.
    * @param errorCallback (Optional) A function to execute if the call fails.
    * @returns The playlist that has been created.
    */
    async createReservation(startDate, endDate, petList, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can make a reservation.");
            const response = await this.axiosClient.post(`reservations`, {
                startDate: startDate,
                endDate: endDate,
                petList: petList
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.reservation;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /*
     * Gets the pet for the given ID.
     * @param id Unique identifier for a pet
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The pet's metadata.
     */
    async viewPet(id, errorCallback) {
        try {
            const response = await this.axiosClient.get(`pets/${id}`);
            return response.data.pet;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /*
    Gets all pets for logged in user.
    @returns all pet metadata.
    */
    async viewAllPets(errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can make a reservation.");
            const response = await this.axiosClient.get(`pets`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.pets;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }


    /*
     * Gets the reservation for the given ID.
     * @param id Unique identifier for a reservation
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The reservation's metadata.
     */
    async viewReservation(id, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can see a reservation.");
            const response = await this.axiosClient.get(`reservations/${id}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.reservation;
        } catch (error) {
            this.handleError(error, errorCallback)
        }

    }

    /*
     * Gets all the reservations for the ownerID (taken from cognito).
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The reservation's metadata.
     */
    async viewAllReservations(errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can make a reservation.");
            const response = await this.axiosClient.get(`reservations`, 
             {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.reservationList;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async updateReservation(id1, id2, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can make a reservation.");
            const response = await this.axiosClient.get(`reservations/${id1}/${id2}`,
            {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.updateReservation;
            }   catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /*
    * Deletes single reservation for reservation Id
    */
    async cancelReservation(id, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can make a reservation.");
            const response = await this.axiosClient.delete(`reservations/${id}`,
            {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.cancelReservation;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Helper method to log the error and run any error functions.
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
    handleError(error, errorCallback) {
        console.error(error);

        const errorFromApi = error?.response?.data?.error_message;
        if (errorFromApi) {
            console.error(errorFromApi)
            error.message = errorFromApi;
        }

        if (errorCallback) {
            errorCallback(error);
        }
    }
}
