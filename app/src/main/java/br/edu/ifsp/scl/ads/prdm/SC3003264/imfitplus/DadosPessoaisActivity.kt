package br.edu.ifsp.scl.ads.prdm.SC3003264.imfitplus

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.prdm.SC3003264.imfitplus.databinding.ActivityDadosPessoaisBinding

class DadosPessoaisActivity: AppCompatActivity() {

    private lateinit var binding: ActivityDadosPessoaisBinding

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
                val idade = binding.etIdade.text.toString()
                val altura = binding.etAltura.text.toString().toDouble()
                val peso = binding.etPeso.text.toString().toDouble()
                val sexo = if (binding.rgSexo.checkedRadioButtonId == R.id.rbMasculino) "M" else "F"
                val nivelAtividade = binding.spinnerAtividade.selectedItemPosition

                val imc = peso / (altura * altura)

                val intent = Intent(this, ResultadoImcActivity::class.java).apply {
                    putExtra("nome", nome)
                    putExtra("idade", idade)
                    putExtra("sexo", sexo)
                    putExtra("altura", altura)
                    putExtra("peso", peso)
                    putExtra("nivelAtividade", nivelAtividade)
                    putExtra("imc", imc)
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


}