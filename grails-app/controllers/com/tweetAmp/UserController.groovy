package com.tweetAmp

import grails.plugin.springsecurity.SpringSecurityService
import org.springframework.dao.DataIntegrityViolationException

class UserController {
    SpringSecurityService springSecurityService
    UserService userService

    static allowedMethods = [save: "POST", delete: "POST"]

    def index(String q) {
        List<User> users = User.createCriteria().list(max: params.max, offset: params.offset) {
            if (q) {
                or {
                    ilike('email', "%${q}%")
                    ilike('username', "%${q}%")
                }
                order(params.sort ?: 'username', params.order ?: 'asc')
            }
        }
        render(view: 'list', model: [userInstanceList: users, userInstanceTotal: users.totalCount])
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [userInstanceList: User.list(params), userInstanceTotal: User.count()]
    }

    def edit(Long id) {
        User userInstance = id ? User.get(id) : null
        if (!userInstance) {
            flash.error = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), null])
            redirect(action: "list")
        } else {
            [userInstance: userInstance]
        }
    }

    def update(Long id, Long version) {
        User userInstance = id ? User.get(id) : null
        if (!userInstance) {
            flash.error = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (userInstance.version > version) {
                userInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'user.label', default: 'User')] as Object[],
                        "Another user has updated this User while you were editing")
                render(view: "create", model: [userInstance: userInstance])
                return
            }
        }

        userInstance.properties = params
        if(params.role){
            userService.updateRoleForExistingUser(userInstance, Role.findById(params.long('role')))
        }

        if (!userInstance.save(flush: true)) {
            render(view: "create", model: [userInstance: userInstance])
            return
        }

        if (id) {
            flash.success = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
        } else {
            flash.success = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
        }
        redirect(action: "show", id: userInstance.id)
    }

    def show(Long id) {
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.error = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
            return
        }

        [userInstance: userInstance]
    }

    def delete(Long id) {
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.error = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
            return
        }

        try {
            userInstance.delete(flush: true)
            flash.success = message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.error = message(code: 'default.not.deleted.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "show", id: id)
        }
    }

    def profile(){
        User user = springSecurityService.currentUser as User

        [userInstance : user]
    }

    def editProfile(){
        User user = springSecurityService.currentUser as User

        [userInstance : user]
    }

    def updateProfile(){
        User userInstance = springSecurityService.currentUser as User
        userInstance.email = params.email
        userInstance.organisation=params.organisation

        if (!userInstance.save(flush: true)) {
            render(view: "editProfile", model: [userInstance: userInstance])
            return
        }


        flash.success = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
        redirect(action: "profile")
    }
}
