import com.tweetAmp.Role

class BootStrap {

    def init = { servletContext ->
            new Role(authority:"ROLE_ADMIN").save(flush: true);
            new Role(authority:"ROLE_USER").save(flush: true);
    }
    def destroy = {
    }
}
