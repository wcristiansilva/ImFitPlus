package br.edu.ifsp.scl.ads.prdm.SC3003264.imfitplus

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.prdm.SC3003264.imfitplus.databinding.ActivityPesoIdealBinding

class PesoIdealActivity : AppCompatActivity (){

    private lateinit var binding : ActivityPesoIdealBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPesoIdealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent: Intent = intent

        val altura = intent.getDoubleExtra("altura", 0.0)
        val pesoAtual = intent.getDoubleExtra("peso", 0.0)

        val pesoIdeal = 22 * (altura * altura)
        val diferenca = pesoAtual - pesoIdeal

        binding.tvPesoIdeal.text = String.format("Peso Ideal: %.1f Kg", pesoIdeal)
        val msg = if (diferenca > 0 ) {
            "Você está ${String.format("%.1f", diferenca)} Kg acima do seu peso ideal."
        } else if (diferenca < 0) {
            "Você está ${String.format("%.1f", -diferenca)} Kg abaixo do seu peso ideal."
        } else {
            "Você está no seu peso ideal! :D"
        }
        binding.tvDiferenca.text = msg

        if (diferenca == 0.0) {
            binding.ivPeso.setImageResource(R.drawable.ic_check)
        } else if (kotlin.math.abs(diferenca) <= 3) {
            binding.ivPeso.setImageResource(R.drawable.ic_alerta)
        } else {
            binding.ivPeso.setImageResource(R.drawable.ic_warning)
        }

        binding.btnReiniciar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }
}