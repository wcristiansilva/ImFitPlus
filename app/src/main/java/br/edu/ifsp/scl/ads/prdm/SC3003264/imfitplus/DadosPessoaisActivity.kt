package br.edu.ifsp.scl.ads.prdm.SC3003264.imfitplus

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.prdm.SC3003264.imfitplus.Data.ImFitPlusDao
import br.edu.ifsp.scl.ads.prdm.SC3003264.imfitplus.Model.CalculationHistory
import br.edu.ifsp.scl.ads.prdm.SC3003264.imfitplus.Model.User
import br.edu.ifsp.scl.ads.prdm.SC3003264.imfitplus.databinding.ActivityDadosPessoaisBinding

class DadosPessoaisActivity: AppCompatActivity() {

    private lateinit var binding: ActivityDadosPessoaisBinding
    private val dao by lazy { ImFitPlusDao(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDadosPessoaisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurando Spinner
        val niveis = resources.getStringArray(R.array.niveis_atividade)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, niveis)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerAtividade.adapter = adapter

        binding.btnCalcularImc.setOnClickListener {
            if (validarCampos()) {
                val nome = binding.etNome.text.toString()
                val idade = binding.etIdade.text.toString().toInt()
                val altura = binding.etAltura.text.toString().toDouble()
                val peso = binding.etPeso.text.toString().toDouble()
                val sexo = if (binding.rgSexo.checkedRadioButtonId == R.id.rbMasculino) "M" else "F"
                val nivelAtividadeTexto = binding.spinnerAtividade.selectedItem.toString()


                val newUser = User(
                    id = 0,
                    name = nome,
                    age = idade,
                    gender = sexo,
                    height = altura,
                    weight = peso,
                    activityLevel = nivelAtividadeTexto
                )

                val userId = dao.saveUser(newUser)

                // Falta criar as funções de calculo abaixo

                val imc = peso / (altura * altura)
                val tmb = calculaTMB(peso, altura, idade, sexo)
                val pesoIdeal = calculaPesoIdeal(altura)
                val categoriaImc = obterCategoriaImc(imc)

                val newCalculo = CalculationHistory(
                    id = 0,
                    userId = userId,
                    imc = imc,
                    imcCategory = categoriaImc,
                    tmb = tmb,
                    idealWeight = pesoIdeal,
                    timestamp = System.currentTimeMillis()
                )

                dao.saveCalculation(newCalculo)

                val intent = Intent(this, ResultadoImcActivity::class.java).apply {
                    putExtra("IMC_RESULTADO", imc)
                    putExtra("IMC_CATEGORIA", categoriaImc)
                    putExtra("TMB_RESULTADO", tmb)
                    putExtra("IDEAL_WEIGHT_RESULTADO", pesoIdeal)
                    putExtra("USER_ID", userId)
                }
                startActivity(intent)
            }
        }
    }

    private fun validarCampos(): Boolean {
        val nome = binding.etNome.text.toString().trim()
        val idadeStr = binding.etIdade.text.toString().trim()
        val alturaStr = binding.etAltura.text.toString().trim()
        val pesoStr = binding.etPeso.text.toString().trim()

        if ( nome.isEmpty() || idadeStr.isEmpty() || alturaStr.isEmpty() || pesoStr.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            return false
        }

        val idade = idadeStr.toIntOrNull()
        val altura = alturaStr.toDoubleOrNull()
        val peso = pesoStr.toDoubleOrNull()

        if (idade == null || idade !in 10..100){
            Toast.makeText(this, "Idade inválida!", Toast.LENGTH_SHORT).show()
            return false
        }
        if (altura == null || altura <= 0) {
            Toast.makeText(this, "Altura inválida!", Toast.LENGTH_SHORT).show()
            return false
        }
        if (peso == null || peso <= 0 ) {
            Toast.makeText(this, "Peso inválido!", Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.rgSexo.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Selecione o sexo!", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun obterCategoriaImc(imc: Double): String {
        return when {
            imc < 18.5 -> "Abaixo do peso"
            imc < 24.9 -> "Peso normal"
            imc < 29.9 -> "Sobrepeso"
            imc < 34.9 -> "Obesidade Grau I"
            imc < 39.9 -> "Obesidade Grau II"
            else -> "Obesidade Grau III"
        }
    }

    private fun calculaTMB(peso: Double, altura: Double, idade: Int, sexo: String): Double {
        return if (sexo == "M"){
            88.362 + (13.397 * peso) + (4.799 * (altura * 100)) - (5.677 * idade)
        } else {
            447.593 + (9.247 * peso) + (3.098 * (altura * 100)) - (4.330 * idade)
        }
    }

    private fun calculaPesoIdeal(altura: Double): Double {
        return (altura * altura) * 22.5
    }

}