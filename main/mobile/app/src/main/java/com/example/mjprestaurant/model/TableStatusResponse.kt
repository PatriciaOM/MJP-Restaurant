package com.example.mjprestaurant.model

/**
 * Resposta al endrpoint table-status amb la llista de taules.
 *
 * @property tables Llista de taules amb el estat actual
 */
data class TableStatusResponse(
    val tables: ArrayList<TableStatus>
)