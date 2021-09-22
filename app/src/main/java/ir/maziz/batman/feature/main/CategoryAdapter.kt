package ir.maziz.batman.feature.main

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ir.maziz.batman.R

class CategoryAdapter(
    val categoryDrawables: ArrayList<Drawable>,
    val categoryTitles: ArrayList<String>
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryIv = itemView.findViewById<ImageView>(R.id.categoryIv)
        val categoryTv = itemView.findViewById<TextView>(R.id.categoryTv)
        fun bind(position: Int) {
            categoryIv.setImageDrawable(categoryDrawables[position])
            categoryTv.text = categoryTitles[position]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_category, parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(position)

    override fun getItemCount(): Int = categoryDrawables.size
}