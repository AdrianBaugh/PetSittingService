import RiverPetSittingClient from '../api/riverPetSittingClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed for the get pet page of the website.
 */
class ViewAllPets extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addPetsToPage'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addPetsToPage);

        this.header = new Header(this.dataStore);
        console.log("ViewAllPets constructor");
    }

    /**
     * Once the client is loaded, get the pet metadata.
     */
    async clientLoaded() {
        //This is undefined Right Now
        const pets = await this.client.viewAllPets();
        this.dataStore.set('pets', pets);
    }

    /**
     * Add the header to the page and load the PetClient.
     */
    mount() {

        this.header.addHeaderToPage();
        this.client = new RiverPetSittingClient();
        this.clientLoaded();
    }

    /**
     * When the pet is updated in the datastore, update the pet metadata on the page.
     */

    addPetsToPage() {
        console.log("add pets to page is starting");
        //This is undefined right now 
        const pets = this.dataStore.get('pets')
        console.log("Pets")

        if (pets == null) {
            return;
        }
        let petsHtml = '<table><tr><th>Name</th></tr>';
        for (const pet of pets) {
           petsHtml += `
           <tr>
               <td>
                   <a href="/viewPet.html?id=${pet.petId}">${pet.petName}</a>
               </td>
           </tr>`;
         }


         document.getElementById('petList').innerHTML = petsHtml;
        console.log("add pets to page is finished");

    }
//                <li class="pet">
//                <span class="petName">PetName: $(pet.petName)</span>
//                </li>            `;
//            <li class="pets">
//                document.getElementById('petName').innerText = pet.petName;
//
//            }
//            document.getElementById('petList').innerHTML = petHtml;


}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const viewAllPets = new ViewAllPets();
    viewAllPets.mount();
};

window.addEventListener('DOMContentLoaded', main);
