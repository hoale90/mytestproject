package com.example.hoale.tikitest

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.util.*
import kotlin.collections.ArrayList

class CategoryAdapter(var list: ArrayList<String>) : RecyclerView.Adapter<CategoryAdapter.CategoryViewholder>() {
    private var listener: OnItemClicktener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewholder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CategoryViewholder, position: Int) {
        val data = list[position]
        holder.tvItem.text = formatString(data)
        // set random background
        val drawable = holder.tvItem.background.mutate()
        val rnd = Random()
        val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(255))
        (drawable as? GradientDrawable)?.setColor(color)

        holder.itemView.setOnClickListener { listener?.onItemClick(data, position) }
    }

    fun setData(list: ArrayList<String>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: OnItemClicktener) {
        this.listener = listener
    }

    private fun formatString(strValue: String?): String {
        if (TextUtils.isEmpty(strValue))
            return ""
        var result = ""
        val arr = strValue!!.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        when (arr.size) {
            0, 1 -> return strValue
            2 -> return strValue.replace(" ", "\n")
            else -> {
                val mid = (arr.size / 2) - 1
                arr[mid] = arr[mid] + "\n"

                for (item in arr) {
                    result += "$item "
                }
            }
        }
        return result.trim()
    }

    class CategoryViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvItem = itemView.findViewById<TextView>(R.id.itemCategory_tvValue)
    }

    interface OnItemClicktener {
        fun onItemClick(data: Any, position: Int)
    }
}