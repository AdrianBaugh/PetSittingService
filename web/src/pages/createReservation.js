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
        this.bindClassMethods(['clientLoaded', 'mount', 'submit', 'redirectToViewReservation', `addPetsToPage`], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.redirectToViewReservation);
        this.dataStore.addChangeListener(this.addPetsToPage);
        this.header = new Header(this.dataStore);
    }

        /**
     * Once the client is loaded, get the pet metadata.
     */
        async clientLoaded() {
            const pets = await this.client.viewAllPets();
            this.dataStore.set('pets', pets);
        }

    /**
     * Add the header to the page and load the PetSittingClient.
     */
    mount() {
        document.getElementById('create').addEventListener('click', this.submit.bind(this));
        this.header.addHeaderToPage();
        
        this.client = new RiverPetSittingClient();
        this.clientLoaded();
    }

    /**
     * When the pet is updated in the datastore, update the pet metadata on the page.
     */

    addPetsToPage() {
        console.log("add pets to page is starting");
        const pets = this.dataStore.get('pets');
        console.log("Pets", pets);
        
        if (pets == null) {
            return;
        }
        
        // Get the checkbox container element
        const checkboxContainer = document.getElementById("checkbox-container");
        checkboxContainer.innerHTML = ''; // Clear any existing content
        
        pets.forEach((item, index) => {
            // Create a checkbox container
            const checkboxDiv = document.createElement("div");
            checkboxDiv.classList.add("custom-checkbox");
        
            // Create the checkbox input element
            const checkboxInput = document.createElement("input");
            checkboxInput.type = "checkbox";
            checkboxInput.id = `checkbox-${index}`;
            checkboxInput.value = item.petName; // Set the value attribute to the pet's name
        
            // Create the label for the checkbox
            const label = document.createElement("label");
            label.setAttribute("for", `checkbox-${index}`);
            label.textContent = item.petName;
        
            // Append the checkbox and label to the container
            checkboxDiv.appendChild(checkboxInput);
            checkboxDiv.appendChild(label);
        
            // Append the container to the checkbox container
            checkboxContainer.appendChild(checkboxDiv);
        });
        
        console.log("add pets to page is finished");
        }
          

    /**
     * Method to run when the create reservation submit button is pressed. Call the MusicPlaylistService to create the
     * reservation.
     */
    async submit(evt) {
        evt.preventDefault();
    
        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = '';
        errorMessageDisplay.classList.add('hidden');
    
        const createButton = document.getElementById('create');
        const origButtonText = createButton.innerText;
        createButton.innerText = 'Creating...';
    
        const startDate = document.getElementById('startDate').value;
        const endDate = document.getElementById('endDate').value;
    
        // Get the selected checkboxes
        const selectedCheckboxes = document.querySelectorAll('#checkbox-container input[type="checkbox"]:checked');
        const petList = Array.from(selectedCheckboxes).map(checkbox => checkbox.value);
    
        if (petList.length === 0) {
            errorMessageDisplay.innerText = 'Please select at least one pet.';
            errorMessageDisplay.classList.remove('hidden');
            createButton.innerText = origButtonText;
            return;
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
            console.log("Redirecting to viewReservation.html");
            window.location.href = `/viewReservation.html?id=${reservation.reservationId}`;
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
