import BindingClass from "../util/bindingClass";

export default class Header extends BindingClass {
    constructor() {
        super();

        const methodsToBind = [
            'addHeaderToPage', 'createSiteTitle'
        ];
        this.bindClassMethods(methodsToBind, this);
    }



async addHeaderToPage() {
    const currentUser = await this.client.getIdentity();

    const siteTitle = this.createSiteTitle();
    const userInfo = this.createUserInfoForHeader(currentUser);

    const header = document.getElementById('header');
    header.appendChild(siteTitle);
    header.appendChild(userInfo);
}

createSiteTitle() {
    const homeButton = document.createElement('a');
    homeButton.classList.add('header_home');
    homeButton.href = 'index.html';
    homeButton.innerText = 'Playlists';

    const siteTitle = document.createElement('div');
    siteTitle.classList.add('site-title');
    siteTitle.appendChild(homeButton);

    return siteTitle;
}
}
const main = async () => {
    console.log('I created a new page!');
};
window.addEventListener('DOMContentLoaded', main);
