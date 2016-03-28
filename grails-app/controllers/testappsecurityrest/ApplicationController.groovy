package testappsecurityrest

import grails.converters.JSON
import grails.core.GrailsApplication
import grails.util.Environment
import grails.plugins.*

class ApplicationController implements PluginManagerAware {

    GrailsApplication grailsApplication
    GrailsPluginManager pluginManager

    def index() {
        [grailsApplication: grailsApplication, pluginManager: pluginManager]
    }

    def testSecureJSON() {
        def ret = [hello: 'world secure']
        println 'in testJSecureSON ' + params
        render ret as JSON
    }
}
