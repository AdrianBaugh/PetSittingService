import RiverPetSittingClient from '../api/riverPetSittingClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import { formatDateToMMDDYYYY } from '../util/dateUtils';

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
        document.getElementById('start-date').innerText = formatDateToMMDDYYYY(reservation.startDate);
        document.getElementById('end-date').innerText = formatDateToMMDDYYYY(reservation.endDate);
        document.getElementById('status').innerText = reservation.status;
       
        let petListHtml = '';
        let petList;
        for (petList of reservation.petList) {
            petListHtml += '<div class="pet">' + petList + '</div>';
        }
        document.getElementById('pet-list').innerHTML = petListHtml;
    }

    redirectToCancellation() {
        const reservation = this.dataStore.get('reservation');
        const cancelButton = document.getElementById('cancelReservationButton');
        const messageContainer = document.getElementById('messageContainer');
        const cancelMessage = document.getElementById('message');

        this.client.cancelReservation(reservation.reservationId);
        
        cancelButton.style.display = 'none';
        messageContainer.style.display = 'block';
  
        // Set the message content
        cancelMessage.textContent = 'This reservation has been canceled!';

        setTimeout(() => {
            // goes to the previous page user was on
            //window.history.back();
            // goes to the reservation options page
            window.location.href = `/reservationOptions.html`;
          }, 3000); //3000 milli = 3 sec
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
