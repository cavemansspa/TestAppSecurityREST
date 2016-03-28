import com.testappsecurityrest.Role
import com.testappsecurityrest.User
import com.testappsecurityrest.UserRole

class BootStrap {

    def init = { servletContext ->

        def admin, guest, roleAdmin, roleUser
        if (!User.count()) {
            admin = new User("admin", "admin").save(failOnError: true)
            guest = new User("guest", "guest").save(failOnError: true)
        }
        if (!Role.count()) {
            roleAdmin = new Role("ROLE_ADMIN").save(failOnError: true)
            roleUser = new Role("ROLE_USER").save(failOnError: true)
        }
        if (!UserRole.count()) {
            UserRole.create(admin, roleAdmin)
            UserRole.create(guest, roleUser)
        }

    }
    def destroy = {
    }
}
