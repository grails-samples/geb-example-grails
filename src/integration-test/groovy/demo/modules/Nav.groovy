package demo.modules

import geb.Module

class Nav extends Module {

    static content = {
        navLink { $('a', text: contains(it)) }
    }

    private void select(String linkText) {
        navLink(linkText).click()
    }

    void home() {
        select('Home')
    }

    void rooms() {
        select('Room List')
    }

    void extras() {
        select('Extra List')
    }
}

