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
//      this.dataStore.addChangeListener(this.addPetToPage);
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
//        const pe = await this.client.getPet(petId);
//        this.dataStore.set('pet', pet);
//        document.getElementById('pets').innerText = "(loading pets...)";

        const pet = await this.client.getPet(petId);
        this.dataStore.set('pet', pet);
    }

    /**
     * Add the header to the page and load the MusicPlaylistClient.
     */
    mount() {
//     document.getElementById('add-pet').addEventListener('click', this.addPet);
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

        let tagHtml = '';
        let tag;
        for (tag of pet.tags) {
            tagHtml += '<div class="tag">' + tag + '</div>';
        }
        document.getElementById('tags').innerHTML = tagHtml;
    }
    }

//    /**
//     * When the songs are updated in the datastore, update the list of pets on the page.
//     */
//    addPetsToPage() {
//        const pets = this.dataStore.get('pets')
//
//        if (pets == null) {
//            return;
//        }
//
//        let petHtml = '';
//        let pet;
//        for (pet of pets) {
//            petHtml += `
//                <li class="pet">
//                    <span class="title">${pet.title}</span>
//                    <span class="album">${pet.album}</span>
//                </li>
//            `;
//        }
//        document.getElementById('pets').innerHTML = petHtml;
//    }
//
//    /**
//     * Method to run when the add song pet submit button is pressed. Call the PtService to add a pet to the
//     * petList.
//     */
//    async addPet() {
//
//        const errorMessageDisplay = document.getElementById('error-message');
//        errorMessageDisplay.innerText = ``;
//        errorMessageDisplay.classList.add('hidden');
//
//        const pet = this.dataStore.get('pet');
//        if (pet == null) {
//            return;
//        }
//
//        document.getElementById('add-pet').innerText = 'Adding...';
//        const asin = document.getElementById('album-asin').value;
//        const trackNumber = document.getElementById('track-number').value;
//        const petId = pet.id;
//
//        const petList = await this.client.addSongToPlaylist(playlistId, asin, trackNumber, (error) => {
//            errorMessageDisplay.innerText = `Error: ${error.message}`;
//            errorMessageDisplay.classList.remove('hidden');
//        });
//
//        this.dataStore.set('pets', petList);
//
//        document.getElementById('add-pet').innerText = 'Add Pet';
//        document.getElementById("add-pet-form").reset();
//    }
//}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const getPet = new GetPet();
    getPet.mount();
};

window.addEventListener('DOMContentLoaded', main);
