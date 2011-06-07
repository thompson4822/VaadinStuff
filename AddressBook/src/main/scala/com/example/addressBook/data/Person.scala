package com.example.addressBook.data

import reflect.BeanProperty

/**
 * Created by IntelliJ IDEA.
 * User: Steve
 * Date: 6/6/11
 * Time: 6:33 AM
 * To change this template use File | Settings | File Templates.
 */


class Person(@BeanProperty var firstName: String = "",
             @BeanProperty var lastName: String = "",
             @BeanProperty var email: String = "",
             @BeanProperty var phoneNumber: String = "",
             @BeanProperty var streetAddress: String = "",
             @BeanProperty var postalCode: Int = 0,
             @BeanProperty var city: String = "") extends Serializable