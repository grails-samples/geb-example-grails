package demo.browserautomation.modules

import geb.Module

class TableModule extends Module {

    static content = {
        entityTable(required: false) { $('table', 0) }
        entityRow { entityRows[it].module EntityRow }
        entityRows(required: false) { entityTable.find('tbody').find('tr') }
    }

    int numberOfRows() {
        if ( entityTable.empty ) {
            return 0
        }
        entityRows.size()
    }

    void select(String name, int columnIndex = 0) {
        for ( int i = 0; i < numberOfRows(); i++ ) {
            def entityRow = entityRow(i)
            if ( entityRow.cellText(columnIndex) == name ) {
                entityRow.showLink().click()
                break
            }
        }
    }

    String textAt(int row, int column) {
        entityRow(row).cellText(column)
    }
}
