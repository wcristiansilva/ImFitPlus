package br.edu.ifsp.scl.ads.prdm.SC3003264.imfitplus

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.prdm.SC3003264.imfitplus.databinding.ActivityGastoCaloricoBinding

class GastoCaloricoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGastoCaloricoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGastoCaloricoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nome = intent.getStringExtra("nome") ?: "Usuário"
        val idade = intent.getIntExtra("idade", 0)
        val sexo = intent.getStringExtra("sexo") ?: "M"
        val altura = intent.getDoubleExtra("altura", 0.0)
        val peso = intent.getDoubleExtra("peso", 0.0)
        val nivelAtividade = intent.getIntExtra("nivelAtividade", 0)

        val alturaCm = altura * 100
        val tmb = if (sexo == "M") {
            66 + (13.7 * peso) + (5 * alturaCm) - (6.8 * idade)
        } else {
            655 + (9.6 * peso) + (1.8 * alturaCm) - (4.7 * idade)
        }

        val fatores = doubleArrayOf(1.2, 1.375, 1.55, 1.725)
        val fator = fatores[nivelAtividade]
        val gastoCalorico = tmb * fator

        binding.tvGasto.text = String.format("Gasto Calórico Diário: %.0f kcal", gastoCalorico)

        binding.btnPesoIdeal.setOnClickListener {
            val intent = Intent(this, PesoIdealActivity::class.java).apply {
                putExtra("nome", nome)
                putExtra("altura", altura)
                putExtra("peso", peso)
            }
            startActivity(intent)
        }
    }
}