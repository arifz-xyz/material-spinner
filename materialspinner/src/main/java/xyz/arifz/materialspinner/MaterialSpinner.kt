package xyz.arifz.materialspinner

import android.content.Context
import android.content.res.ColorStateList
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.Filterable
import android.widget.ListAdapter
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputLayout
import xyz.arifz.materialspinner.ExtensionFunctions.dpToPx

class MaterialSpinner : TextInputLayout {

    private lateinit var autoCompleteTextView: AppCompatAutoCompleteTextView

    init {
        setupTheme()
    }

    constructor(context: Context) : super(context) {
        init(context, null, R.style.TextInputLayoutStyle)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(
        context,
        attrs,
        R.style.TextInputLayoutStyle
    ) {
        init(context, attrs, R.style.TextInputLayoutStyle)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context, attrs, R.style.TextInputLayoutStyle)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyle: Int) {
        setupView(context)
        setupAttributes(context, attrs)
        initWatchers()
    }

    private fun setupTheme() {
        boxBackgroundColor = ContextCompat.getColor(context, R.color.color_white)
        boxBackgroundMode = BOX_BACKGROUND_OUTLINE
        boxStrokeWidth = 2
        boxStrokeWidthFocused = 2
        boxStrokeColor = ContextCompat.getColor(context, R.color.color_blue_crayola)
        setHintTextAppearance(R.style.TextInputLayoutHintTextStyle)
    }

    private fun setupView(context: Context) {

        autoCompleteTextView = AppCompatAutoCompleteTextView(context)

        autoCompleteTextView.layoutParams =
            LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 50.dpToPx())
        autoCompleteTextView.background = null
        autoCompleteTextView.setLines(1)
        autoCompleteTextView.maxLines = 1
        autoCompleteTextView.isSingleLine = true
        autoCompleteTextView.isClickable = false
        autoCompleteTextView.isFocusable = false
        autoCompleteTextView.isFocusableInTouchMode = false
        autoCompleteTextView.isCursorVisible = false
        autoCompleteTextView.inputType = InputType.TYPE_NULL
        addView(autoCompleteTextView)
        autoCompleteTextView.setPadding(20, 20, 20, 20)
        autoCompleteTextView.setOnClickListener { autoCompleteTextView.showDropDown() }
    }

    private fun setupAttributes(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.MaterialSpinner)
            try {
                var hint = a.getString(R.styleable.MaterialSpinner_hint)
                val isRequired = a.getBoolean(R.styleable.MaterialSpinner_isRequired, false)
                if (isRequired) {
                    if (hint.isNullOrEmpty())
                        hint = ""
                    hint += " *"
                    this.hint = hint
                } else this.hint = hint

                val isReadOnly = a.getBoolean(R.styleable.MaterialSpinner_isReadOnly, false)
                setReadOnly(isReadOnly)

                val radius = a.getFloat(R.styleable.MaterialSpinner_radius, 5f)
                setBoxCornerRadii(radius, radius, radius, radius)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            a.recycle()
        }
    }

    private fun setReadOnly(state: Boolean) {
        if (state) {
            isClickable = false
            isFocusable = false
            isFocusableInTouchMode = false
            setReadOnlyColor()
        }
    }

    private fun setReadOnlyColor() {
        val colorStateList = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_enabled, android.R.attr.state_enabled)
            ),
            intArrayOf(
                ContextCompat.getColor(context, R.color.color_light_grey),
                ContextCompat.getColor(context, R.color.color_light_grey)
            )
        )
        setBoxStrokeColorStateList(colorStateList)
        boxBackgroundColor = ContextCompat.getColor(context, R.color.color_white)
        autoCompleteTextView.setTextColor(ContextCompat.getColor(context, R.color.color_light_grey))
    }

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)
        autoCompleteTextView.showDropDown()
    }

    private fun initWatchers() {
        autoCompleteTextView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                isErrorEnabled = false
            }
        })
    }

    fun <T> setAdapter(adapter: T) where T : ListAdapter, T : Filterable {
        autoCompleteTextView.setAdapter(adapter)
    }

    fun setSelection(position: Int) {
        try {
            autoCompleteTextView.setSelection(position)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun clearSelection() {
        try {
            autoCompleteTextView.setSelection(-1)
            autoCompleteTextView.setText(null, false)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    var text: String?
        get() {
            return autoCompleteTextView.text?.toString()
        }
        set(value) {
            autoCompleteTextView.setText(value, false)
        }

}