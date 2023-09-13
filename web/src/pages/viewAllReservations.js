import RiverPetSittingClient from '../api/riverPetSittingClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import { formatDateToMMDDYYYY } from '../util/dateUtils';

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
        //loadin icon?
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
        let reservationHtml = '<table><tr><th>Reservation ID</th><th>Start Date</th><th>End Date</th></tr>';

        for (const reservation of reservations) {
            reservationHtml += `
            <tr>
                <td>
                    <a href="/viewReservation.html?id1=${reservation.petOwnerId}&id2=${reservation.reservationId}">${reservation.reservationId}</a>
                </td>    
                    <td>${formatDateToMMDDYYYY(reservation.startDate)}</td>
                    <td>${formatDateToMMDDYYYY(reservation.endDate)}</td>
                </tr>
            `;
        }
        document.getElementById('reservationList').innerHTML = reservationHtml;
    }
}

const main = async () => {
    const viewAllReservations = new ViewAllReservations();
    viewAllReservations.mount();
};

window.addEventListener('DOMContentLoaded', main);