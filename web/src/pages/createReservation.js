import RiverPetSittingClient from '../api/riverPetSittingClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

/**
 * Logic needed for the create playlist page of the website.
 */
class CreateReservation extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'redirectToViewReservation'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.redirectToViewReservation);
        this.header = new Header(this.dataStore);
    }

    /**
     * Add the header to the page and load the MusicPlaylistClient.
     */
    mount() {
        document.getElementById('create').addEventListener('click', this.submit);

        this.header.addHeaderToPage();

        this.client = new RiverPetSittingClient();
    }

    /**
     * Method to run when the create reservation submit button is pressed. Call the MusicPlaylistService to create the
     * reservation.
     */
    async submit(evt) {
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const createButton = document.getElementById('create');
        const origButtonText = createButton.innerText;
        createButton.innerText = 'Loading...';

        const startDate = document.getElementById('startDate').value;
        const endDate = document.getElementById('endDate').value;
        const petsText = document.getElementById('pets').value;



        let petList;
        if (petsText.length < 1) {
            petList = null;
        } else {
            petList = petsText.split(/\s*,\s*/);
        }

        const reservation = await this.client.createReservation(startDate, endDate, petList, (error) => {
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('reservation', reservation);
    }

    /**
     * When the playlist is updated in the datastore, redirect to the view playlist page.
     */
    redirectToViewReservation() {
        const reservation = this.dataStore.get('reservation');
        if (reservation != null) {
            window.location.href = `/viewReservation.html?id1=${reservation.petOwnerId}&id2=${reservation.reservationId}`;
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const createReservation = new CreateReservation();
    createReservation.mount();
};

window.addEventListener('DOMContentLoaded', main);