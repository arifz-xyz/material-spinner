package xyz.arifz.demo

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import xyz.arifz.demo.databinding.ActivityDemoBinding

class DemoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setupSpinner()
//        initListener()
    }

//    private fun setupSpinner() {
//        val datas = arrayOf("Male", "Female", "Others")
//        binding.spn.setAdapter(ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item , datas))
//
//        binding.spn.hint = "Gender"
//        binding.spn.text = "Female"
//    }
//
//    private fun initListener() {
//        binding.btn.setOnClickListener {
//            val txt = binding.spn.text?.toString()
//            if (txt.isNullOrEmpty()) {
//                Toast.makeText(
//                    this,
//                    txt ?: "empty",
//                    Toast.LENGTH_SHORT
//                ).show()
//                binding.spn.error = "Error occurred"
//            } else {
//                binding.spn.text = null
//            }
//        }
//    }

}