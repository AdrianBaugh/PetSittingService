import RiverPetSittingClient from '../api/riverPetSittingClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';


class CreatePet extends BindingClass {
    
    constructor () {
        super();
        this.bindClassMethods(['mount', 'submit', 'redirectToViewPet'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.redirectToViewPet);
        this.header = new Header(this.dataStore);

    }

    mount() {
            document.getElementById('create').addEventListener('click', this.submit);

            this.header.addHeaderToPage();

            this.client = new RiverPetSittingClient();
        }


    async submit(evt) {
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const createButton = document.getElementById('create');
        const origButtonText = createButton.innerText;
        createButton.innerText = 'Loading. . .';

        const petName = document.getElementById('pet-name').value;
        
        const pet = await this.client.CreatePet(petName, (error) => {
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('pet', pet);
    }

    redirectToViewPet() {
        const pet = this.dataStore.get('pet');
        if (pet != null) {
            window.location.href = `/pet.html?id=${pet.id}`;
        }
    }
}
    const main = async () => {
        const createPet = new CreatePet();
        createPet.mount;
    };

    window.addEventListener('DOMContentLoaded', main);

