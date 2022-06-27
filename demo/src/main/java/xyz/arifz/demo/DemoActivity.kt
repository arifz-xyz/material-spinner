package xyz.arifz.demo

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import xyz.arifz.demo.databinding.ActivityDemoBinding

class DemoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupSpinner()
        initListener()
    }

    private fun setupSpinner() {
        val datas = arrayOf("Male", "Female", "Others")
        binding.spn.setItems(datas)

        binding.spn.hint = "Gender"
        binding.spn.text = "Female"

        binding.spn.onItemClickListener { p0, p1, p2, p3 ->
            Log.v(
                "Hello",
                "onItemClickListener"
            )
        }
    }

    private fun initListener() {
        binding.btn.setOnClickListener {
            val txt = binding.spn.text
            if (txt.isNullOrEmpty()) {
                Toast.makeText(this, txt ?: "empty", Toast.LENGTH_SHORT).show()
                binding.spn.error = "Error occurred"
            } else {
                binding.spn.text = null
            }
        }
    }

}