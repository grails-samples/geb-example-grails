package demo.pages

import geb.Module

class ScaffoldNav extends Module {

    static content = {
        navLink { $('a', text: contains(it)) }
    }

    void select(String linkText) {
        navLink(linkText).click()
    }
}
