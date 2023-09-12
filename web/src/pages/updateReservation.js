import RiverPetSittingClient from '../api/riverPetSittingClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

/**
 * Logic needed for the create playlist page of the website.
 */
class UpdateReservation extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'redirectToUpdateReservation'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.redirectToUpdateReservation);
        this.header = new Header(this.dataStore);
    }

    /**
     * Add the header to the page and load the MusicPlaylistClient.
     */
    mount() {
        document.getElementById('update').addEventListener('click', this.submit);

        this.header.addHeaderToPage();

        this.client = new RiverPetSittingClient();

        const reservation = this.dataStore.get("reservation");
        if(reservation){
        document.getElementById('startDate').value = reservation.startDate;
        document.getElementById("endDate").value = reservation.endDate;
        }
    }

    /**
     * Method to run when the update reservation submit button is pressed. Call the RiverPetSittingClient to update the
     * reservation.
     */
    async submit(evt) {
        evt.preventDefault();

        // option 1
        // Get the current date
        const currentDate = new Date();

        // Format the current date as YYYY-MM-DD (the required format for input type="date")
        const year = currentDate.getFullYear();
        const month = String(currentDate.getMonth() + 1).padStart(2, '0'); // Adding 1 because months are 0-indexed
        const day = String(currentDate.getDate()).padStart(2, '0');
        const formattedDate = `${year}-${month}-${day}`;

        // Set the minimum attribute of the date input
        document.getElementById('startDate').min = formattedDate;
        document.getElementById('endDate').min = formattedDate;

        // //option 2
        // document.getElementById("startDate").min = new Date().toISOString().split("T")[0];
        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const createButton = document.getElementById('updating');
        const origButtonText = createButton.innerText;
        createButton.innerText = 'updating...';


        const startDate = document.getElementById('startDate').value;
        const endDate = document.getElementById('endDate').value;
        const petsText = document.getElementById('pets').value;

        let petList;
        if (petsText.length < 1) {
            petList = null;
        } else {
            petList = petsText.split(/\s*,\s*/);
        }

       try{
        //Make a updateReservationRequest
        const reservation = await this.client.updateReservation(
          startDate,
          endDate,
          petList,
          (error) => {
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('reservation', updateReservation);
          }catch(error) {
        console.error(error);
          }
    }

    /**
     * When the pet is updated in the datastore, redirect to the Update Reservation page.
     */
    redirectToUpdateReservation() {
        const reservation = this.dataStore.get('reservation');
        if (reservation != null) {
            window.location.href = `/updateReservation.html?id1=${reservation.petOwnerId}&id2=${reservation.reservationId}`;
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const updateReservation = new UpdateReservation();
    updateReservation.mount();
};

window.addEventListener('DOMContentLoaded', main);
