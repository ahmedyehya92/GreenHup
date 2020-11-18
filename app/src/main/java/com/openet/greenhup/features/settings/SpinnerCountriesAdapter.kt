package com.openet.greenhup.features.settings

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.openet.entities.Country

class SpinnerCountriesAdapter(
    context: Context, textViewResourceId: Int,
    values: Array<Country>
) : ArrayAdapter<Country?>(context, textViewResourceId, values) {
    // Your sent context

    private var list: Array<Country>? =null

    // Your custom values for the spinner (User
    override fun getItem(position: Int): Country {
        return list!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun getCountryPosition(id: String): Int?
    {
        list?.forEachIndexed { index, country ->
            if(country.id==id)
                return index
        }
        return null
    }

    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        val label: TextView = super.getView(position, convertView, parent) as TextView
        label.setTextColor(Color.BLACK)
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(list!![position].name)

        // And finally return your dynamic (or custom) view for each spinner item
        return label
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    override fun getDropDownView(
        position: Int, convertView: View?,
        parent: ViewGroup?
    ): View {
        val label: TextView = super.getDropDownView(position, convertView, parent) as TextView
        label.setTextColor(Color.BLACK)
        label.setText(list!![position].name)
        return label
    }

    init {
        this.list= values
    }
}

