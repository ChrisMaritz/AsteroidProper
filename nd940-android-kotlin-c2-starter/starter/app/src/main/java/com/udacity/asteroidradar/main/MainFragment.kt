package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.Recycler.Adapter
import com.udacity.asteroidradar.Recycler.Model
import com.udacity.asteroidradar.data.AsteroidData
import com.udacity.asteroidradar.data.AsteroidDatabase
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainFragment : Fragment() {
    lateinit var recyclerView: RecyclerView;

    val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        setHasOptionsMenu(true)
        val adapter = Adapter(iterateThrough())

        recyclerView = binding.asteroidRecycler
        recyclerView.adapter = adapter


        return binding.root
    }
    fun iterateThrough(): MutableList<Model>{
        var database = mutableListOf<AsteroidData?>()
        CoroutineScope(Dispatchers.IO).launch {
             database = AsteroidDatabase.getInstance(requireContext()).asteroidDatabaseDao.get()
        }
        var number = 0
        val model1 = mutableListOf<Model>()
        for(item in database){
            var model = Model(
                item?.id!!.toInt(),
                item.PotentialHazard
            )
            model1.add(model)
        }

        return model1
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
