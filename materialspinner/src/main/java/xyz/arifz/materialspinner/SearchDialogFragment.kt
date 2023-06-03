package xyz.arifz.materialspinner

import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.DialogFragment
import java.text.DecimalFormat

class SearchDialogFragment : DialogFragment() {
    private var searchView: SearchView? = null
    private var listView: ListView? = null
    private var ivClose: ImageView? = null
    private var items = ArrayList<String>()
    private var adapter: ArrayAdapter<String>? = null
    private var tvTitle: TextView? = null
    private var title: String? = null

    private var realHeight: Int = 0
    private var realWidth: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            items = getStringArrayList(KEY_ITEMS) as? ArrayList<String> ?: ArrayList()
            title = getString(KEY_TITLE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_fragment_search, container, false)
        calculateDeviceResolution(requireActivity())
        return view
    }


    private fun calculateDeviceResolution(context: Activity) {
        val display = if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            context.display
        } else {
            @Suppress("DEPRECATION")
            context.windowManager.defaultDisplay
        }
        val realMetrics = DisplayMetrics()
        display?.getRealMetrics(realMetrics)
        realWidth = realMetrics.widthPixels
        Log.d("OrderDialog", "$realWidth")
        realHeight = realMetrics.heightPixels
        Log.i("Q#_dialog_fragment", "realwidth:$realWidth >=< realHeight$realHeight")
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.let { window ->
            val layoutParams = window.attributes
            layoutParams.height = realHeight / 2
            window.attributes = layoutParams
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchView = view.findViewById(R.id.search_view)
        listView = view.findViewById(R.id.list_view)
        ivClose = view.findViewById(R.id.iv_close)
        tvTitle = view.findViewById(R.id.tv_title)

        initTitle()
        setupListView()
        setupSearchView()
        initListener()
    }

    private fun initTitle() {
        tvTitle?.text = title
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

    fun getPercentage(total: Int, percent: Int) = (total * percent) / 100

    fun generateCommaSeparatedValue(value: Double): String {
        val myFormatter = DecimalFormat("#,##,###")
        return myFormatter.format(value)
    }


    companion object {
        private const val TAG = "SearchDialogFragment"

        fun newInstance(items: ArrayList<String>?, title: String? = "") =
            SearchDialogFragment().apply {
                val args = Bundle()
                items?.let {
                    args.putStringArrayList(
                        KEY_ITEMS,
                        it
                    )
                    args.putString (KEY_TITLE, title)
                }
                arguments = args
            }
    }
}