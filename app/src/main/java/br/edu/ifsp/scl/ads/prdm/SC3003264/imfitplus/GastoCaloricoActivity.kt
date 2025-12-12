package br.edu.ifsp.scl.ads.prdm.SC3003264.imfitplus

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.prdm.SC3003264.imfitplus.databinding.ActivityGastoCaloricoBinding
import java.text.DecimalFormat

class GastoCaloricoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGastoCaloricoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGastoCaloricoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nome = intent.getStringExtra("nome") ?: "Usuário"
        val altura = intent.getDoubleExtra("altura", 0.0)
        val pesoIdeal = intent.getDoubleExtra("pesoIdeal", 0.0)
        val pesoAtual = intent.getDoubleExtra("pesoAtual", 0.0)
        val nivelAtividadePosicao = intent.getIntExtra("nivelAtividadePosicao", 0)
        val tmb = intent.getDoubleExtra("TMB_RESULTADO", 0.0)


        val fatores = doubleArrayOf(1.2, 1.375, 1.55, 1.725, 1.9)
        val fator = fatores[nivelAtividadePosicao]
        val gastoCalorico = tmb * fator
        val df = DecimalFormat("#.##")

        binding.tvGasto.text = "Gasto Calórico Diário Ideal: ${df.format(gastoCalorico)} kcal"

        binding.btnPesoIdeal.setOnClickListener {
            val intent = Intent(this, PesoIdealActivity::class.java).apply {
                putExtra("nome", nome)
                putExtra("altura", altura)
                putExtra("pesoIdeal", pesoIdeal)
                putExtra("pesoAtual", pesoAtual)
            }
            startActivity(intent)
        }
    }
}