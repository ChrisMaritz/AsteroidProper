package com.udacity.asteroidradar.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavAction
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.Recycler.Adapter
import com.udacity.asteroidradar.Recycler.Model
import com.udacity.asteroidradar.api.ImageApi
import com.udacity.asteroidradar.api.getImageOfTheDay
import com.udacity.asteroidradar.data.AsteroidData
import com.udacity.asteroidradar.data.AsteroidDatabase
import com.udacity.asteroidradar.data.ImageDatabase
import com.udacity.asteroidradar.data.ImageOfDay
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import kotlinx.android.synthetic.main.asteroid_visual.view.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.coroutines.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment : Fragment() {
    lateinit var recyclerView: RecyclerView;
    private lateinit var adapter: Adapter
    val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        var database = AsteroidDatabase.getInstance(requireContext())
        var asteroidDatabaseDao = database.asteroidDatabaseDao
        var databaseImage = ImageDatabase.getInstance(requireContext())
        var imageDatabaseDao = databaseImage?.imageOfDayDao

        val imageView = binding.activityMainImageOfTheDayLayout.activity_main_image_of_the_day
        val imageText = binding.textView
        var imageOfDay = CoroutineScope(Dispatchers.Main).launch {


            val image2 = imageDatabaseDao?.get()

                if(image2?.mediaType == "image"){
                Picasso.with(context).load(image2?.image).into(imageView);
                    imageText.text = image2.title
                    imageText.contentDescription = image2.title
                    imageView.contentDescription = image2.title
            }
            }



        adapter = Adapter(asteroidDatabaseDao)
        iterateThrough()

        recyclerView = binding.asteroidRecycler
        recyclerView.adapter = adapter

        return binding.root
    }

    fun iterateThrough(){

        viewLifecycleOwner.lifecycleScope.launch {
            val databaseAsteroids = withContext(Dispatchers.IO) {
                AsteroidDatabase.getInstance(requireContext()).asteroidDatabaseDao.get()
            }
            val modelsList = databaseAsteroids.map { item ->
                Model(
                    item?.id!!.toInt(),
                    item.PotentialHazard,
                    item.absoluteMagnitude,
                    item.estimatedDiameterMax.toLong(),
                    item.kmPerSecond.toLong(),
                    item.astroMissDistance.toLong()
                )
            }
            adapter.dataList = modelsList
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
