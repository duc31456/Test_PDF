package com.example.myapplication.Activity.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Activity.MainActivity
import com.example.myapplication.Model.ImportModel
import com.example.myapplication.Model.ToolModel
import com.example.myapplication.R

class ToolAdapter(val context: MainActivity) : RecyclerView.Adapter<ToolAdapter.viewHolder>() {
    var toolFile: ArrayList<ToolModel> = ArrayList()
    fun setdata(list_tool: ArrayList<ToolModel>) {
        this.toolFile = list_tool
        notifyDataSetChanged()
    }
    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var hinh: ImageView = itemView.findViewById(R.id.image_import)
        var ten: TextView = itemView.findViewById(R.id.ten_import)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_import, parent, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val list: ToolModel = toolFile.get(position)
        if (list == null) {
            return
        } else {
            holder.hinh.setImageResource(list.url)
            holder.ten.setText(list.ten)
        }
    }

    override fun getItemCount(): Int {
        return toolFile.size
    }
}