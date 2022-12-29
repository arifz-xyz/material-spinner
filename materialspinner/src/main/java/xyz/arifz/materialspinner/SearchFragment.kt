package xyz.arifz.materialspinner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.DialogFragment


class SearchFragment : DialogFragment() {
    var searchView: SearchView? = null
    var listView: ListView? = null
    val dataList = ArrayList<String>()
    var adapter: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    private fun init(view: View){
        searchView = view.findViewById(R.id.sv_search)
        listView = view.findViewById(R.id.lv_items)

        dataList.add("Ami")
        dataList.add("Tumi")
        dataList.add("She")

        adapter = ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,dataList)
        listView?.adapter = adapter

        setUpSearchView()
        initListeners()

    }

    private fun initListeners(){
        listView?.setOnItemClickListener  { _, _, position, _ ->
            val element = adapter?.getItem(position)
            Toast.makeText(requireContext(),element,Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpSearchView(){
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (dataList.contains(query)) {
                    adapter!!.filter.filter(query)
                } else {
                    // Search query not found in List View
                    Toast.makeText(requireContext(), "Not found", Toast.LENGTH_LONG).show()
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter!!.filter.filter(newText)
                return false
            }
        })
    }
}