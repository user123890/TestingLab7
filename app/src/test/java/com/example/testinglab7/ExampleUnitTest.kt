
package com.example.testinglab7

import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito

// Интерфейс для проверки работы с репозиторием
interface OrderRepository {
    fun saveOrder(total: Int): Boolean
    fun getTotal(): Int
}

class ExampleUnitTest {

    // 1–10. Обычные Unit-тесты

    @Test
    fun calculateTotalNormalValues() {
        assertEquals(1500, calculateTotal(500, 3))
    }

    @Test
    fun calculateTotalOneItem() {
        assertEquals(500, calculateTotal(500, 1))
    }

    @Test
    fun calculateTotalZeroPrice() {
        assertEquals(0, calculateTotal(0, 5))
    }

    @Test
    fun calculateTotalZeroQuantity() {
        assertEquals(0, calculateTotal(500, 0))
    }

    @Test
    fun calculateTotalLargeValues() {
        assertEquals(10000000, calculateTotal(100000, 100))
    }

    @Test
    fun calculateTotalSmallValues() {
        assertEquals(1, calculateTotal(1, 1))
    }

    @Test
    fun validOrderNormalValues() {
        assertTrue(isValidOrder(500, 3))
    }

    @Test
    fun validOrderZeroPrice() {
        assertTrue(isValidOrder(0, 3))
    }

    @Test
    fun invalidOrderNegativePrice() {
        assertFalse(isValidOrder(-100, 3))
    }

    @Test
    fun invalidOrderZeroQuantity() {
        assertFalse(isValidOrder(500, 0))
    }

    // 11. Mockito: имитация сохранения заказа

    @Test
    fun mockRepositorySaveOrder() {
        val repository = Mockito.mock(OrderRepository::class.java)

        Mockito.`when`(repository.saveOrder(1500)).thenReturn(true)

        assertTrue(repository.saveOrder(1500))
    }

    // 12. Mockito: заглушка для получения суммы

    @Test
    fun stubRepositoryReturnsTotal() {
        val repository = Mockito.mock(OrderRepository::class.java)

        Mockito.`when`(repository.getTotal()).thenReturn(1500)

        assertEquals(1500, repository.getTotal())
    }

    // 13. Mockito: проверка вызова метода

    @Test
    fun verifyRepositoryInteraction() {
        val repository = Mockito.mock(OrderRepository::class.java)

        repository.saveOrder(1500)

        Mockito.verify(repository).saveOrder(1500)
    }

    // 14. Mockito: проверка отсутствия сохранения неверного заказа

    @Test
    fun invalidOrderDoesNotCallRepository() {
        val repository = Mockito.mock(OrderRepository::class.java)

        if (isValidOrder(-100, 3)) {
            repository.saveOrder(calculateTotal(-100, 3))
        }

        Mockito.verify(repository, Mockito.never())
            .saveOrder(Mockito.anyInt())
    }

    // 15. Mockito: проверка отрицательного результата сохранения

    @Test
    fun repositoryReturnsFalse() {
        val repository = Mockito.mock(OrderRepository::class.java)

        Mockito.`when`(repository.saveOrder(1500)).thenReturn(false)

        assertFalse(repository.saveOrder(1500))
    }
}
