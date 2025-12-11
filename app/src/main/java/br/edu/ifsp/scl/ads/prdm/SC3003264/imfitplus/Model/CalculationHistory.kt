package br.edu.ifsp.scl.ads.prdm.SC3003264.imfitplus.Model

data class CalculationHistory(
    val id: Long,
    val userId: Long,
    val imc: Double,
    val imcCategory: String,
    val tmb: Double,
    val idealWeight: Double,
    val timestamp: Long
)
