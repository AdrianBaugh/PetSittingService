import PetClient from '../api/PetClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed for the get pet page of the website.
 */
class GetPet extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addPetToPage'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addPetToPage);

        this.header = new Header(this.dataStore);
        console.log("GetPet constructor");
    }

    /**
     * Once the client is loaded, get the pet metadata.
     */
    async clientLoaded() {
        const urlParams = new URLSearchParams(window.location.search);
        const petId = urlParams.get('id');
        document.getElementById('pet-name').innerText = "Loading Pet ...";


        const pet = await this.client.getPet(petId);
        this.dataStore.set('pet', pet);
    }

    /**
     * Add the header to the page and load the PetClient.
     */
    mount() {

        this.header.addHeaderToPage();
        this.client = new PetClient();
        this.clientLoaded();
    }

    /**
     * When the pet is updated in the datastore, update the pet metadata on the page.
     */
    addPetToPage() {
        const pet = this.dataStore.get('pet');
        if (pet == null) {
            return;
        }

        document.getElementById('pet-name').innerText = pet.name;
        document.getElementById('owner-name').innerText = pet.customerName;

    }
    }



/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const getPet = new GetPet();
    getPet.mount();
};

window.addEventListener('DOMContentLoaded', main);
