package org.d3if0020.assesment2mobpro.data

import androidx.annotation.DrawableRes
import org.d3if0020.assesment2mobpro.R

data class Pizza(
    val id: Int,
    val name: String,
    @DrawableRes val image: Int,
    val description: String,
    val price: String,
    var quantity: Int = 0,
    val category: String
)

val pizzaList = listOf(
    Pizza(
        id = 1,
        image = R.drawable.two,
        name = "Fire Beef",
        description = "Beef Burger, Mozzarella Cheese, Sweet Sauce, Classic, And Chili sauce",
        price = "Rp.103.000",
        category = "Starter",

    ),
    Pizza(
        id = 2,
        image = R.drawable.three,
        name = "Frankfurter BBQ",
        description = "Beef Frankfurter, Minced Beef, And Mozzarella",
        price = "Rp.107.000",
        category = "Starter"
    ),
    Pizza(
        id = 3,
        image = R.drawable.four,
        name = "Meat Monsta",
        description = "Beef Frankfuurter, Smoked Beef, And Minced Beef",
        price = "Rp.120.000",
        category = "Starter"
    ),
    Pizza(
        id = 4,
        image = R.drawable.three,
        name = "Meat Lover",
        description = "Beef Sausages, Minced Beef and Beef Burgers",
        price = "Rp.110.000",
        category = "Starter"
    ),
    Pizza(
        id = 5,
        image = R.drawable.two,
        name = "American Favourite",
        description = "Beef Pepperoni, Minced Beef, And Mozazrella Cheese",
        price = "Rp.125.000",
        category = "Classic"
    ),
    Pizza(
        id = 6,
        image = R.drawable.three,
        name = "Super Supreme",
        description = "Smoked Beef, Beef Chicken and Beef Burgers",
        price = "Rp.102.000",
        category = "Classic"
    ),
    Pizza(
        id = 7,
        image = R.drawable.four,
        name = "Cheeseburger",
        description = "Murstard Sauce And Mozzarella Cheese",
        price = "Rp.75.000",
        category = "Asian"
    ),
    Pizza(
        id = 8,
        image = R.drawable.two,
        name = "Pepperoni",
        description = "Beef Pepperoni And Mozzarella Cheese",
        price = "Rp.67.000",
        category = "Asian"
    ),
)

fun getPizzaById(pizzaId: Int): Pizza? {
    return pizzaList.find { it.id == pizzaId }
}
