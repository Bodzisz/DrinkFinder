package io.github.drinkfinder.ingredientList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Filter
import android.widget.TextView
import io.github.drinkfinder.R

class IngredientListAdapter(private var ingredients: ArrayList<ListIngredientDataModel>, mContext: Context) :
    ArrayAdapter<ListIngredientDataModel>(mContext, R.layout.ingredient_item, ingredients) {

    val allIngredients = ArrayList(ingredients)

    private class ViewHolder {
        lateinit var ingredientName : TextView
        lateinit var checkbox : CheckBox
    }

    override fun getCount(): Int {
        return ingredients.size
    }

    override fun getItem(position: Int): ListIngredientDataModel {
        return ingredients[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val myConvertView: View?
        val viewHolder: ViewHolder
        val result: View
        if(convertView == null) {
            viewHolder = ViewHolder()
            myConvertView = LayoutInflater.from(parent.context)
                .inflate(R.layout.ingredient_item, parent, false)
            viewHolder.ingredientName = myConvertView.findViewById(R.id.ingredientName)
            viewHolder.checkbox = myConvertView.findViewById(R.id.checkbox)
            result = myConvertView
            myConvertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
            result = convertView
        }

        val item: ListIngredientDataModel = getItem(position)
        viewHolder.ingredientName.text = item.name
        viewHolder.checkbox.isChecked = item.checked
        viewHolder.checkbox.isClickable = false
        return result
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                val filteredIngredients = ArrayList<ListIngredientDataModel>()
                addMatchingIngredients(filteredIngredients, p0)
                filterResults.count = filteredIngredients.size
                filterResults.values = filteredIngredients
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                if (p1 != null) {
                    ingredients.clear()
                    @Suppress("UNCHECKED_CAST")
                    ingredients.addAll(p1.values as ArrayList<ListIngredientDataModel>)
                    notifyDataSetChanged()
                }
            }

            fun addMatchingIngredients(filteredIngredients: ArrayList<ListIngredientDataModel>, query: CharSequence?) {
                for(ingredient in allIngredients) {
                    if(query?.let { ingredient.name?.contains(it, ignoreCase = true) } == true) {
                        filteredIngredients.add(ingredient)
                    }
                }
            }
        }
    }
}