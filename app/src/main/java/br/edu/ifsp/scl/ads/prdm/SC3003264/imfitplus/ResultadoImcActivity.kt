package br.edu.ifsp.scl.ads.prdm.SC3003264.imfitplus

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.prdm.SC3003264.imfitplus.databinding.ActivityResultadoImcBinding

class ResultadoImcActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultadoImcBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultadoImcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nome = intent.getStringExtra("nome") ?: "Usuário"
        val imc = intent.getDoubleExtra("imc", 0.0)

        binding.tvNome.text = "Olá, $nome!"
        binding.tvImc.text = String.format("IMC: %.2f", imc)

        var categoria = ""
        when {
            imc < 18.5 -> {
                categoria = "Abaixo do peso"
                binding.ivImc.setImageResource(R.drawable.ic_baixo)
            }
            imc <= 24.9 -> {
                categoria = "Normal"
                binding.ivImc.setImageResource(R.drawable.ic_normal)
            }
            imc <= 29.9 -> {
                categoria = "Sobrepeso"
                binding.ivImc.setImageResource(R.drawable.ic_sobrepeso)
            }
            else -> {
                categoria = "Obesidade"
                binding.ivImc.setImageResource(R.drawable.ic_obesidade)
            }
        }
        binding.tvCategoria.text = categoria

        binding.btnGastoCalorico.setOnClickListener {
            val intent = Intent(this, GastoCaloricoActivity::class.java).apply {
                putExtra("nome", nome)
                putExtra("idade", intent.getIntExtra("idade", 0))
                putExtra("sexo", intent.getStringExtra("sexo"))
                putExtra("altura", intent.getDoubleExtra("altura", 0.0))
                putExtra("peso", intent.getDoubleExtra("peso", 0.0))
                putExtra("nivelAtividade", intent.getIntExtra("nivelAtividade", 0))
            }
            startActivity(intent)
        }
        binding.btnVoltar.setOnClickListener {finish()}
    }
}