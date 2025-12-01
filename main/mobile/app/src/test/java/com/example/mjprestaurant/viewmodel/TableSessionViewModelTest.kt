package com.example.mjprestaurant.viewmodel

import com.example.mjprestaurant.model.dish.Dish
import com.example.mjprestaurant.model.dish.DishCategory
import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * Proves unitàries per a TableSessionViewModel.
 *
 * Ens centrem en la lògica local del carret de la comanda,
 * ja que no requereix connexió de xarxa ni mocks complexos.
 *
 * @see TableSessionViewModel
 * @author Martin Muñoz Pozuelo
 */
class TableSessionViewModelTest {

    /**
     * Prova que el carret s'inicialitza buit.
     */
    @Test
    fun cartShouldBeEmptyInitially() {
        // Arrange
        val viewModel = TableSessionViewModel()

        // Assert
        assertThat(viewModel.cartItems).isEmpty()
    }

    /**
     * Prova que afegir un plat l'insereix correctament al carret local.
     *
     * Given: Un ViewModel i un plat de prova.
     * When: S'afegeix el plat al carret.
     * Then: El carret ha de contenir 1 element i ha de ser aquest plat.
     */
    @Test
    fun addingDishShouldUpdateCart() {
        // Arrange
        val viewModel = TableSessionViewModel()
        val dish = Dish(
            id = 1L,
            name = "Paella",
            description = "Arròs",
            price = 12.5f,
            category = DishCategory.MAIN,
            available = true
        )

        // Act
        viewModel.addToCart(dish)

        // Assert
        assertThat(viewModel.cartItems).hasSize(1)
        assertThat(viewModel.cartItems[0].name).isEqualTo("Paella")
    }

    /**
     * Prova que es poden afegir múltiples plats al carret.
     */
    @Test
    fun addingMultipleDishesShouldAccumulateInCart() {
        // Arrange
        val viewModel = TableSessionViewModel()
        val dish1 = Dish(1L, "Aigua", "Botella", 2.0f, DishCategory.DRINK, true)
        val dish2 = Dish(2L, "Cafè", "Sol", 1.5f, DishCategory.DRINK, true)

        // Act
        viewModel.addToCart(dish1)
        viewModel.addToCart(dish2)

        // Assert
        assertThat(viewModel.cartItems).hasSize(2)
        assertThat(viewModel.cartItems).containsExactly(dish1, dish2)
    }
}