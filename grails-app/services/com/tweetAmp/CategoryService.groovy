package com.tweetAmp

import grails.transaction.Transactional

@Transactional
class CategoryService {

    def updateUsersForCategories(Category category, List<User> userList) {
        if (userList*.id != category.users*.id) {
            Set<User> categoryUsers = category.users ?:[]
            categoryUsers?.each { User user ->
                if (!userList?.contains(user)) {
                    user.removeFromCategories(category)
                }
            }
            category.save(flush: true, failOnError: true)

            userList.each { User user ->
                if (!categoryUsers?.contains(user)) {
                    user.addToCategories(category)
                }
            }
            category.save(flush: true, failOnError: true)
        }
    }
}
