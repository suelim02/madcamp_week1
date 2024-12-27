package com.example.week1.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.week1.R

class Myadapter(private val teamList: List<Team>) : RecyclerView.Adapter<Myadapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)    {
        val teamText: TextView = itemView.findViewById(R.id.team_name)
        val stadiumText: TextView = itemView.findViewById(R.id.team_stadium)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_teamlist, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentTeam = teamList[position]
        holder.teamText.text = currentTeam.name
        holder.stadiumText.text = currentTeam.stadium
    }

    override fun getItemCount() = teamList.size
}