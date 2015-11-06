package com.tweetAmp

import org.springframework.dao.DataIntegrityViolationException

class CategoryController {
    CategoryService categoryService

    static allowedMethods = [save: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [categoryInstanceList: Category.list(params), categoryInstanceTotal: Category.count()]
    }

    def create(Long id) {
        def categoryInstance = id ? Category.get(id) : new Category(params)
        if (!categoryInstance) {
            flash.error = message(code: 'default.not.found.message', args: [message(code: 'category.label', default: categoryInstance.name)])
            redirect(action: "list")
        } else {
            [categoryInstance: categoryInstance]
        }
    }

    def save(Long id, Long version) {
        def categoryInstance = id ? Category.get(id) : new Category(params)
        if (!categoryInstance) {
            flash.error = message(code: 'default.not.found.message', args: [message(code: 'category.label', default: categoryInstance.name)])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (categoryInstance.version > version) {
                categoryInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'category.label', default: 'Category')] as Object[],
                        "Another user has updated this Category while you were editing")
                render(view: "create", model: [categoryInstance: categoryInstance])
                return
            }
        }

        categoryInstance.properties = params

        List<Long> userIds = params.userList ? params.list('userList')*.toLong() : []
        categoryService.updateUsersForCategories(categoryInstance, User.findAllByIdInList(userIds))

        if (!categoryInstance.save(flush: true)) {
            render(view: "create", model: [categoryInstance: categoryInstance])
            return
        }

        if (id) {
            flash.success = message(code: 'default.updated.message', args: [message(code: 'category.label', default: categoryInstance.name)])
        } else {
            flash.success = message(code: 'default.created.message', args: [message(code: 'category.label', default: categoryInstance.name)])
        }
        redirect(action: "show", id: categoryInstance.id)
    }

    def show(Long id) {
        def categoryInstance = Category.get(id)
        if (!categoryInstance) {
            flash.error = message(code: 'default.not.found.message', args: [message(code: 'category.label', default: categoryInstance.name)])
            redirect(action: "list")
            return
        }

        [categoryInstance: categoryInstance]
    }

    def delete(Long id) {
        def categoryInstance = Category.get(id)
        if (!categoryInstance) {
            flash.error = message(code: 'default.not.found.message', args: [message(code: 'category.label', default: categoryInstance.name)])
            redirect(action: "list")
            return
        }

        try {
            categoryInstance.delete(flush: true)
            flash.success = message(code: 'default.deleted.message', args: [message(code: 'category.label', default: categoryInstance.name)])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.error = message(code: 'default.not.deleted.message', args: [message(code: 'category.label', default: categoryInstance.name)])
            redirect(action: "show", id: id)
        }
    }
}
