package br.edu.ifsp.scl.ads.prdm.SC3003264.imfitplus

import android.app.Activity
import android.content.Intent
import android.icu.text.DecimalFormat
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

        val name = intent.getStringExtra("name") ?: "Usuário"
        val idade = intent.getIntExtra("idade", 0)
        val dataNascimento = intent.getStringExtra("dataNascimento") ?: "00/00/0000"
        val imc = intent.getDoubleExtra("IMC_RESULTADO", 0.0)
        val categoriaImc = intent.getStringExtra("IMC_CATEGORIA")
        val tmb = intent.getDoubleExtra("TMB_RESULTADO", 0.0)
        val pesoIdeal = intent.getDoubleExtra("IDEAL_WEIGHT_RESULTADO", 0.0)
        val pesoAtual = intent.getDoubleExtra("pesoAtual", 0.0)
        val nivelAtividadePosicao = intent.getIntExtra("nivelAtividadePosicao", 0)


        val df = DecimalFormat("#.##")
        val imcFormatado = df.format(imc)
        val tmbFormatado = df.format(tmb)
        //val pesoIdealFormatado = df.format(pesoIdeal)

        binding.tvNome.text = "Olá, $name!"
        binding.tvImc.text = String.format("Seu IMC é: %.2f", imc)

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
        binding.tvCategoria.text = dataNascimento.toString()

        binding.btnGastoCalorico.setOnClickListener {
            val intent = Intent(this, GastoCaloricoActivity::class.java).apply {
                putExtra("nome", name)
                putExtra("idade", intent.getIntExtra("idade", 0))
                putExtra("sexo", intent.getStringExtra("sexo"))
                putExtra("altura", intent.getDoubleExtra("altura", 0.0))
                putExtra("pesoIdeal", pesoIdeal)
                putExtra("pesoAtual", pesoAtual)
                putExtra("nivelAtividadePosicao", nivelAtividadePosicao)
                putExtra("TMB_RESULTADO", tmb)
            }
            startActivity(intent)
        }
        binding.btnVoltar.setOnClickListener {finish()}
    }
}