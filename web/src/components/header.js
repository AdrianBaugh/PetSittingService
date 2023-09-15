import RiverPetSittingClient from '../api/riverPetSittingClient';
import BindingClass from "../util/bindingClass";

/**
 * The header component for the website.
 */
export default class Header extends BindingClass {
    constructor() {
        super();

        const methodsToBind = [
            'addHeaderToPage', 'createSiteTitle', 'createUserInfoForHeader',
            'createLoginButton', 'createLoginButton', 'createLogoutButton'
        ];
        this.bindClassMethods(methodsToBind, this);

        this.client = new RiverPetSittingClient();
    }

    /**
     * Add the header to the page.
     */
    async addHeaderToPage() {
        const currentUser = await this.client.getIdentity();

        const siteTitle = this.createSiteTitle();
        const userInfo = this.createUserInfoForHeader(currentUser);

        const header = document.getElementById('header');
        header.appendChild(siteTitle);
        header.appendChild(userInfo);
    }

createSiteTitle() {

    const logo = document.createElement('img');
    logo.src = 'shortRiver.jpg';
    logo.width = 300;
    logo.height = 220;
    const homeButton = document.createElement('a');
    const logoContainer = document.createElement('div');

    const siteTitle = document.createElement('div');

    homeButton.classList.add('header-home');
    homeButton.href = 'index.html';
    const siteLink = document.createElement('a');
    siteLink.href = 'index.html';
    const riverText = document.createElement('span');
    riverText.classList.add('river-text');


    logoContainer.appendChild(logo);
    logoContainer.appendChild(riverText);
    // Append the logo container to the anchor element
    siteLink.appendChild(logoContainer);
    // Append the anchor element to the site title
    siteTitle.appendChild(siteLink);
    siteTitle.appendChild(homeButton);
    return siteTitle;
}
    createUserInfoForHeader(currentUser) {
        const userInfo = document.createElement('div');
        userInfo.classList.add('user');

        const childContent = currentUser
            ? this.createLogoutButton(currentUser)
            : this.createLoginButton();

        userInfo.appendChild(childContent);

        return userInfo;
    }

    createLoginButton() {
        return this.createButton('Login', this.client.login);
    }

    createLogoutButton(currentUser) {
        return this.createButton(`Logout: ${currentUser.name}`, this.client.logout);
    }

    createButton(text, clickHandler) {
        const button = document.createElement('a');
        button.classList.add('button');
        button.href = '#';
        button.innerText = text;

        button.addEventListener('click', async () => {
            await clickHandler();
        });

        return button;
    }
}
