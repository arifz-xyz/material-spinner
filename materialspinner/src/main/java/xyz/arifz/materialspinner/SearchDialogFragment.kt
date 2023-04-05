package xyz.arifz.materialspinner

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.DialogFragment

class SearchDialogFragment : DialogFragment() {
    private var searchView: SearchView? = null
    private var listView: ListView? = null
    private var ivClose: ImageView? = null
    private var items = ArrayList<String>()
    private var adapter: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            items = getStringArrayList(KEY_ITEMS) as? ArrayList<String> ?: ArrayList()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchView = view.findViewById(R.id.search_view)
        listView = view.findViewById(R.id.list_view)
        ivClose = view.findViewById(R.id.iv_close)

        setupListView()
        setupSearchView()
        initListener()
    }

    private fun setupListView() {
        adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, items)
        listView?.adapter = adapter
    }

    private fun setupSearchView() {
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (items.contains(query)) adapter?.filter?.filter(query)
                else Log.d(TAG, "Not found")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter?.filter?.filter(newText)
                return false
            }
        })
    }

    private fun initListener() {
        listView?.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val item = adapter?.getItem(position)
            val bundle = Bundle()
            bundle.putString(KEY_SELECTED_ITEM, item)
            requireActivity().supportFragmentManager.setFragmentResult(KEY_SEARCH, bundle)
            dismiss()
        }

        ivClose?.setOnClickListener { dismiss() }
    }

    override fun onResume() {
        super.onResume()
        searchView?.requestFocus()
    }


    companion object {
        private const val TAG = "SearchDialogFragment"

        fun newInstance(items: ArrayList<String>?) = SearchDialogFragment().apply {
            val args = Bundle()
            items?.let { args.putStringArrayList(KEY_ITEMS, it) }
            arguments = args
        }
    }
}