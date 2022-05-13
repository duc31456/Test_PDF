package com.example.myapplication.Activity.Adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Activity.MainActivity
import com.example.myapplication.Model.ImportModel
import com.example.myapplication.R
import java.util.*
import kotlin.collections.ArrayList

class ImportAdapter(val context: MainActivity) : RecyclerView.Adapter<ImportAdapter.viewHolder>() {
    var importFile: ArrayList<ImportModel> = ArrayList()
    fun setdata(list_import: ArrayList<ImportModel>) {
        this.importFile = list_import
        notifyDataSetChanged()
    }
    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var hinh: ImageView = itemView.findViewById(R.id.image_import)
        var ten: TextView = itemView.findViewById(R.id.ten_import)
        var backgroud: CardView = itemView.findViewById(R.id.backgroud_cardview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_import, parent, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val list: ImportModel = importFile.get(position)
        if (list == null) {
            return
        } else {
            holder.hinh.setImageResource(list.url)
            holder.ten.setText(list.ten)
        }
    }

    override fun getItemCount(): Int {

        return importFile.size
    }
}