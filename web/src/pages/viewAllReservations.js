import RiverPetSittingClient from '../api/riverPetSittingClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed for the view all reservations page of the website.
 */

class ViewAllReservations extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addReservationsToPage'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addReservationsToPage);
        this.header = new Header(this.dataStore);
        console.log("ViewAllReservations constructor");
    }

    async clientLoaded() {
        document.getElementById('reservations').innerText = "(loading reservations. . .)";
        const reservations = await this.client.viewAllReservations();
        this.dataStore.set('reservations', reservations);
    }

    mount() {
        
        this.header.addHeaderToPage();

        this.client = new RiverPetSittingClient();
        this.clientLoaded();
    }


    addReservationsToPage() {
        const reservations = this.dataStore.get('reservations')

        if (reservations == null) {
            return;
        }
        
        let reservationHtml;
        let reservation;
        for (reservation of reservations) {
            reservationHtml += `
                <li class="reservation">
                    <span class="reservationId">${reservation.reservationId}</span>
                </li>
            `;
        }
        document.getElementById('reservations').innerHTML = reservationHtml;
    }
}

const main = async () => {
    const viewAllReservations = new ViewAllReservations();
    viewAllReservations.mount();
};

window.addEventListener('DOMContentLoaded', main);