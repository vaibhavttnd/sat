package com.tweetAmp

import grails.transaction.Transactional

@Transactional
class CategoryService {

    def updateUsersForCategories(Category category, List<User> userList) {
        if (userList*.id != category.users*.id) {
            Set<User> categoryUsers = category.users ?:[]

            // Deleting the mentioned users
            (categoryUsers-userList)?.each {User user ->
                user.removeFromCategories(category)
            }

            //Adding the other mentioned users
            (userList-categoryUsers)?.each { User user ->
                user.addToCategories(category)
            }
            category.save(flush: true, failOnError: true)
        }
    }
}
