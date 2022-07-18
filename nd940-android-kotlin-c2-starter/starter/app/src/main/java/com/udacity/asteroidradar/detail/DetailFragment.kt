package com.udacity.asteroidradar.detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val asteroid = DetailFragmentArgs.fromBundle(requireArguments()).selectedAsteroid

        binding.asteroid = asteroid
        binding.absoluteMagnitude.contentDescription =
            "text indicating absolute magnitude which is " +
            DetailFragmentArgs.
            fromBundle(requireArguments()).
            selectedAsteroid.absoluteMagnitude.toString()

        binding.distanceFromEarth.contentDescription =
            "text indicating distance from earth which is " +
                    DetailFragmentArgs.
                    fromBundle(requireArguments()).
                    selectedAsteroid.distanceFromEarth.toString()

        binding.estimatedDiameter.contentDescription =
            "text indicating estimated diameter which is " +
                    DetailFragmentArgs.
                    fromBundle(requireArguments()).
                    selectedAsteroid.estimatedDiameter.toString()

        binding.relativeVelocity.contentDescription =
            "text indicating relative velocity which is " +
                    DetailFragmentArgs.
                    fromBundle(requireArguments()).
                    selectedAsteroid.relativeVelocity.toString()

        binding.activityMainImageOfTheDay.contentDescription =
            "Image of an asteroid indicating wether it is hazardous or not"

        binding.helpButton.setOnClickListener {
            displayAstronomicalUnitExplanationDialog()
        }

        return binding.root
    }

    private fun displayAstronomicalUnitExplanationDialog() {
        val builder = AlertDialog.Builder(requireActivity())
            .setMessage(getString(R.string.astronomica_unit_explanation))
            .setPositiveButton(android.R.string.ok, null)
        builder.create().show()
    }
}
