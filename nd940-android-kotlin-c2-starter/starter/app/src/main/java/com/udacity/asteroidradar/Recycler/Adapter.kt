package com.udacity.asteroidradar.Recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.data.AsteroidData
import kotlinx.android.synthetic.main.asteroid_visual.view.*
import com.udacity.asteroidradar.data.AsteroidDatabase
import com.udacity.asteroidradar.data.AsteroidDatabaseDao
import com.udacity.asteroidradar.main.MainFragmentDirections
import kotlinx.coroutines.*

class Adapter(var asteroidDatabaseDao: AsteroidDatabaseDao) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var dataList: List<Model> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

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

        CoroutineScope(Dispatchers.Main).launch {
            val asteroid = asteroidDatabaseDao.getAsteroid(dataList.get(position).name)
            var asteroid2 = Asteroid(
                asteroid.id,
                asteroid.absoluteMagnitude,
                asteroid.estimatedDiameterMax,
                asteroid.kmPerSecond,
                asteroid.astroMissDistance,
                asteroid.PotentialHazard
            )
            holder.itemView.setOnClickListener { view ->
                Navigation.findNavController(view).navigate(MainFragmentDirections.actionShowDetail(asteroid2))
            }
        }

        holder.itemView.asteroid_name.text = dataList.get(position).name.toString()
        holder.itemView.asteroid_name.contentDescription = "name of asteroid is ${dataList.get(position).name}"
        holder.itemView.is_hazourdous.text = if(dataList.get(position).hazard){
            "Hazardous"

        }else{
            "Not Hazardous"
        }
        holder.itemView.is_hazourdous.contentDescription = if(dataList.get(position).hazard){
            "Text indicating if asteroid hazardous, it is here"

        }else{
            "Text indicating if asteroid hazardous, it is not here"
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}