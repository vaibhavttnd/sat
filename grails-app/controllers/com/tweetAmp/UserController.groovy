package com.tweetAmp

import org.springframework.dao.DataIntegrityViolationException

class UserController {

    static allowedMethods = [save: "POST", delete: "POST"]

    def index(String q) {
        List<User> users = User.createCriteria().list(max: params.max, offset: params.offset) {
            if (q) {
                or {
                    ilike('email', "%${q}%")
                    ilike('name', "%${q}%")
                }
                order(params.sort ?: 'name', params.order ?: 'asc')
            }
        }
        render(view: 'list', model: [userInstanceList: users, userInstanceTotal: users.totalCount])
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [userInstanceList: User.list(params), userInstanceTotal: User.count()]
    }

    def create(Long id) {
        def userInstance = id ? User.get(id) : new User(params)
        if (!userInstance) {
            flash.error = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
        } else {
            [userInstance: userInstance]
        }
    }

    def save(Long id, Long version) {
        def userInstance = id ? User.get(id) : new User(params)
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
}
