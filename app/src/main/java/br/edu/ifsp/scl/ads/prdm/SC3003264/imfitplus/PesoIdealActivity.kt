package br.edu.ifsp.scl.ads.prdm.SC3003264.imfitplus

import android.content.Intent
import java.text.DecimalFormat
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.prdm.SC3003264.imfitplus.databinding.ActivityPesoIdealBinding


class PesoIdealActivity : AppCompatActivity (){

    private lateinit var binding : ActivityPesoIdealBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPesoIdealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("nome") ?: "Usuário"
        val altura = intent.getDoubleExtra("altura", 0.0)
        val pesoAtual = intent.getDoubleExtra("pesoAtual", 0.0)
        val pesoIdeal = intent.getDoubleExtra("pesoIdeal", 0.0)


        if (altura > 0  && pesoAtual > 0) {
            val diferenca = pesoAtual - pesoIdeal
            val df = DecimalFormat("#.##")

            binding.tvPesoIdeal.text = "Seu peso ideal é de: ${df.format(pesoIdeal)} Kg."
            binding.tvNome.text = "$name"

            val msg = if (diferenca > 0 ) {
                "Você está ${df.format(diferenca)} Kg acima do seu peso ideal."
            } else if (diferenca < 0) {
                "Você está ${df.format(-diferenca)} Kg abaixo do seu peso ideal."
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
        } else {
            binding.tvPesoIdeal.text = "Erro!"
            binding.tvDiferenca.text = "Não foi possível obter os dados para o cálculo."
            binding.ivPeso.setImageResource(R.drawable.ic_warning)
        }


        binding.btnReiniciar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }
}