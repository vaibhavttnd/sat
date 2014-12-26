class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        //"/"(controller: "login",action: "auth")
        //"/"(controller: 'index', action: 'login')
        "/"(controller: "login",action: "auth")
        "500"(view:'/error')

        "/login/$action?"(controller: "login")
        "/logout/$action?"(controller: "logout")

        "/profile"(controller: "user", action: 'profile')
        "/editProfile"(controller: "user", action: 'editProfile')

        /*"/failed"(view:"/failed")
        "/register"(view:"/register")
        "500"(view:'/error')*/
	}
}
