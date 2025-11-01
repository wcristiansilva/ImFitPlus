 package br.edu.ifsp.scl.ads.prdm.SC3003264.imfitplus

 import android.content.Intent
 import android.os.Bundle
 import androidx.appcompat.app.AppCompatActivity
 import br.edu.ifsp.scl.ads.prdm.SC3003264.imfitplus.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnComecar.setOnClickListener {
            val intent = Intent(this, DadosPessoaisActivity::class.java)
            startActivity(intent)
        }
    }
}