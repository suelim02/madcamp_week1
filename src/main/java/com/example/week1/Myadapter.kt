package com.example.week1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Myadapter(private val teamList: List<Team>,
                private val onButtonClick: (Team) -> Unit
) : RecyclerView.Adapter<Myadapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)    {
        val teamText: TextView = itemView.findViewById(R.id.team_name)
        val stadiumText: TextView = itemView.findViewById(R.id.team_stadium)
        val addButton: Button = itemView.findViewById(R.id.btn_add_list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_teamlist_addpage, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentTeam = teamList[position]
        holder.teamText.text = currentTeam.name
        holder.stadiumText.text = currentTeam.stadium
        holder.addButton.setOnClickListener {
            onButtonClick(currentTeam)
        }
    }

    override fun getItemCount() = teamList.size
}