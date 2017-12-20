package demo

import grails.gorm.services.Query
import grails.gorm.services.Service
import grails.gorm.services.Where

@Service(Extra)
interface ExtraService {

    Extra get(Serializable id)

    List<Extra> list(Map args)

    Long count()

    void delete(Serializable id)

    Extra save(Extra extra)

    Extra save(String name)

    Extra update(Serializable id, String name)

    @Where({ id in ids })
    List<Extra> find(List<Long> ids)
}