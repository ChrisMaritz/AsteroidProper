package com.udacity.asteroidradar.Recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.data.AsteroidData
import kotlinx.android.synthetic.main.asteroid_visual.view.*
import com.udacity.asteroidradar.data.AsteroidDatabase
import com.udacity.asteroidradar.data.AsteroidDatabaseDao

class Adapter(private val dataList: MutableList<Model>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


//    fun iterateThrough(){
//        val database: List<AsteroidData?> = AsteroidDatabase.getInstance().asteroidDatabaseDao.get()
//        var number = 0
//        for(item in database){
//            var model = Model(
//                item?.id!!.toInt(),
//                item.PotentialHazard
//            )
//            dataList.add(number,model)
//            number += 1
//        }
//    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name : TextView = itemView.findViewById(R.id.asteroid_name)
        var hazard : TextView = itemView.findViewById(R.id.is_hazourdous)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.asteroid_visual, null)
        return ViewHolder(viewHolder)
    }

    val data = mutableListOf<Model>()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.asteroid_name.text = dataList.get(position).name.toString()
        holder.itemView.is_hazourdous.text = dataList.get(position).hazard.toString()
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}