package com.example.bookproject

import reflect.BeanProperty

/**
 * Created by IntelliJ IDEA.
 * User: Steve
 * Date: 5/30/11
 * Time: 1:17 PM
 * To change this template use File | Settings | File Templates.
 */

class Book(@BeanProperty var isbn: String = "",
           @BeanProperty var title: String,
           @BeanProperty var author: String,
           @BeanProperty var price: Int)