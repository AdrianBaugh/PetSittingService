import RiverPetSittingClient from '../api/riverPetSittingClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed for the get reservation page of the website.
 */
class ViewReservation extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addReservationToPage', 'redirectToCancellation'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addReservationToPage);

        this.header = new Header(this.dataStore);
        console.log("ViewReservation constructor");
    }

    /**
     * Once the client is loaded, get the pet metadata.
     */
    async clientLoaded() {
        const urlParams = new URLSearchParams(window.location.search);
        const reservationId = urlParams.get('id');
//        const petOwnerId = urlParams.get('id1');

        const reservation = await this.client.viewReservation(reservationId);
        this.dataStore.set('reservation', reservation);
    }

    /**
     * Add the header to the page and load the PetClient.
     */
    mount() {

        this.header.addHeaderToPage();
        this.client = new RiverPetSittingClient();
        this.clientLoaded();
        document.getElementById('cancelReservationButton').addEventListener('click', this.redirectToCancellation);

    }

    /**
     * When the reservation is updated in the datastore, update the pet metadata on the page.
     */
    addReservationToPage() {
        const reservation = this.dataStore.get('reservation');
        if (reservation == null) {
            return;
        }

        document.getElementById('reservation-id').innerText = reservation.reservationId;
        console.log("Reservation id : " + reservation.reservationId);
        document.getElementById('owner-id').innerText = reservation.petOwnerId;
        console.log("Owner id : " + reservation.petOwnerId);
        document.getElementById('sitter-id').innerText = reservation.sitterId;
        document.getElementById('start-date').innerText = reservation.startDate;
        document.getElementById('end-date').innerText = reservation.endDate;
        document.getElementById('status').innerText = reservation.status;
       

        //check below if errors with pet list
        let petListHtml = '';
        let petList;
        for (petList of reservation.petList) {
            petListHtml += '<div class="pet">' + petList + '</div>';
        }
        document.getElementById('pet-list').innerHTML = petListHtml;
    }

    redirectToCancellation() {
        const reservation = this.dataStore.get('reservation')

        this.client.cancelReservation(reservation.reservationId);

        // window.location.href = `/cancelReservation.html`;
    }

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const viewReservation = new ViewReservation();
    viewReservation.mount();
};

window.addEventListener('DOMContentLoaded', main);
