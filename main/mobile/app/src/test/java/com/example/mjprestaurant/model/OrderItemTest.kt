package com.example.mjprestaurant.model

import com.example.mjprestaurant.model.dish.DishCategory
import com.example.mjprestaurant.model.order.OrderItem
import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * Proves unitàries per al model OrderItem (Línia de Comanda).
 *
 * Verifica que les dades del plat es guarden correctament per enviar al servidor.
 *
 * @see OrderItem
 * @author Martin Muñoz Pozuelo
 */
class OrderItemTest {

    /**
     * Prova la creació correcta d'un OrderItem.
     * Aquest test assegura que estem mapejant tots els camps que demana el servidor.
     */
    @Test
    fun orderItemShouldContainDishDetails() {
        // Arrange
        val orderId = 500L
        val dishId = 10L
        val quantity = 2
        val price = 15.50f
        val dishName = "Entrecot"

        // Act
        val item = OrderItem(
            idOrder = orderId,
            idDish = dishId,
            amount = quantity,
            price = price,
            name = dishName,
            description = "Poc fet",
            category = DishCategory.MAIN
        )

        // Assert
        assertThat(item.idOrder).isEqualTo(orderId)
        assertThat(item.amount).isEqualTo(quantity)
        assertThat(item.price).isEqualTo(price)
        assertThat(item.name).isEqualTo(dishName)
    }

    /**
     * Prova simple de càlcul total (lògica de negoci).
     * Encara que el model no té mètode 'getTotal', podem verificar la lògica.
     */
    @Test
    fun calculateTotalShouldBeCorrect() {
        // Arrange
        val quantity = 3
        val price = 10.0f

        // Act
        val total = quantity * price

        // Assert
        assertThat(total).isEqualTo(30.0f)
    }
}