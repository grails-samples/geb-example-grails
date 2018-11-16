package demo

trait LeakageDetector {

    abstract getGrailsApplication()

    Map<Class, Integer> leakage = [:]

    private List<Class> getDomainClasses() {
        grailsApplication.getArtefacts("Domain")*.clazz
    }

    int countByDomainClass(Class domain) {
        Class domainClass = grailsApplication.classLoader.loadClass(domain.name)
        int count = 0
        domain.withTransaction(readOnly: true) {
            count = domainClass.count()
        }
        count
    }

    void setupLeakage() {
        List<Class> domainClasses = getDomainClasses()
        for ( Class clazz : domainClasses) {
            leakage[clazz] = countByDomainClass(clazz)
        }
    }

    void verifyLeakage() {
        List<Class> domainClasses = getDomainClasses()
        boolean leakageExists
        for ( Class clazz : domainClasses) {
            int newCount = countByDomainClass(clazz)
            if (leakage[clazz] != newCount) {
                leakageExists = true
                log.error('Leakage! {} old #{} new #{}', clazz.simpleName, leakage[clazz], newCount)
            }
        }
        assert !leakageExists
    }
}

