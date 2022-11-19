package es.unex.giiis.medicinex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import es.unex.giiis.medicinex.adapter.SectionAdapter
import es.unex.giiis.medicinex.databinding.ActivityCatalogueBinding

class Catalogue : AppCompatActivity()
{
    private lateinit var binding : ActivityCatalogueBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityCatalogueBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.closeButtonImage.setOnClickListener {
            closeCatalogue()
        }

        val recyclerView = binding.catalogueRV
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SectionAdapter(CatalogueProvider.categories) { section ->
            onItemSelected(
                section
            )
        }
    }

    private fun closeCatalogue()
    {
        onBackPressed()
    }

    private fun onItemSelected(section: String)
    {
        val intent = Intent(this, Section::class.java)
        intent.putExtra("section", section)
        startActivity(intent)
    }
}