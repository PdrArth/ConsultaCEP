package com.pdrarth.aplicativoconsultacep

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.pdrarth.aplicativoconsultacep.Model.Endereco
import com.pdrarth.aplicativoconsultacep.api.Api
import com.pdrarth.aplicativoconsultacpf.R
import com.pdrarth.aplicativoconsultacpf.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        window.statusBarColor = Color.parseColor("#009688")
        val actionbar = supportActionBar
        actionbar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#009688")))

        //CONFIGURANDO O RETROFIT

        val retrofit = Retrofit.Builder()
            //USADO PARA CONVERTER O GSON QUE VEM DA INTERNET
            .addConverterFactory(GsonConverterFactory.create())
            //PASSA A URL DE BASE DA API
            .baseUrl("https://viacep.com.br/ws/")
            //BUIDAR ELA
            .build()
            //USAR O CREATE PARA PASSAR A INTERFACE
            .create(Api::class.java)

        binding.bucarcep.setOnClickListener { view ->
            val cep = binding.cep.text.toString()

            if (cep.isEmpty()) {
                Toast.makeText(this, "Preencha o CEP", Toast.LENGTH_SHORT).show()
            } else {
                //CAPTURAR OS METODOS QUE ESTA NA NOSSA API(INTERFACE) E PASSA O CALLBACK PASSANDO OBJETO
                retrofit.setEndereco(cep).enqueue(object : Callback<Endereco> {
                    //METODOS QUE VEM NO OBJECT
                    override fun onResponse(call: Call<Endereco>, response: Response<Endereco>) {
                        //A RESPONDA 200 E SE ESTIVER TUDO CERTO
                        if (response.code() == 200) {
                            //AI PASSAMOS O CORPO DA RESPOSTA E PEGA O QUE VOCE QUER ATRAVES DA STRING
                            val logradouro = response.body()?.logradouro.toString()
                            val bairro = response.body()?.bairro.toString()
                            val localidade = response.body()?.localidade.toString()
                            val uf = response.body()?.uf.toString()
                            setFormulario(logradouro, bairro, localidade, uf)
                        } else {
                            snakbar2(view)
                        }
                    }

                    override fun onFailure(call: Call<Endereco>, t: Throwable) {
                        snakbar(view)
                    }


                })
            }

        }


    }
    //Atualiza os campos do formulário com os dados do endereço retornado pela API
    private fun setFormulario(logradoro: String, bairro: String, localidade: String, uf: String) {

        binding.logradouro.setText(logradoro)
        binding.bairro.setText(bairro)
        binding.cidade.setText(localidade)
        binding.estado.setText(uf)

    }

    private fun snakbar(view: View) {
        val snackbar = Snackbar.make(view, "Error inesperado", Snackbar.LENGTH_INDEFINITE)
        snackbar.setAction("ok") {

        }
        snackbar.setTextColor(Color.WHITE)
        snackbar.setBackgroundTint(Color.BLACK)
        snackbar.setTextColor(Color.WHITE)
        snackbar.show()

    }

    private fun snakbar2(view: View) {
        val snackbar = Snackbar.make(view, "Cep invalidoo", Snackbar.LENGTH_INDEFINITE)
        snackbar.setAction("ok") {

        }
        snackbar.setTextColor(Color.WHITE)
        snackbar.setBackgroundTint(Color.BLACK)
        snackbar.setTextColor(Color.WHITE)
        snackbar.show()


    }


}