package com.example.sportingbike.ui.fragment.game

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sportingbike.R
import com.example.sportingbike.databinding.FragmentGameStartBinding
import com.example.sportingbike.ui.fragment.menu.MainFragment
import com.example.sportingbike.utils.Constants

class GameStartFragment : Fragment() {
    private lateinit var binding: FragmentGameStartBinding
    private lateinit var viewModel: GameStartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameStartBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this)[GameStartViewModel::class.java]

        val windowManager =
            requireActivity().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels

        binding.startBtn.setOnClickListener{
            val bundle = Bundle().apply {
                putInt(Constants.INDEX_SKIN, viewModel.selectedImage.value as Int)
                putInt(Constants.INDEX_SPEED, viewModel.selectedSpeed.value as Int)
            }
            val destinationFragment = GameFragment()
            destinationFragment.arguments = bundle

            createFragment(destinationFragment)
        }

        binding.backBtn.setOnClickListener{
            createFragmentBack()
        }

        binding.skinIV.setImageResource(viewModel.rightArrowSkin())
        binding.speedTV.text = viewModel.leftArrowSpeed(screenHeight).toString()

        binding.rightArrowSkin.setOnClickListener{
            binding.skinIV.setImageResource(viewModel.rightArrowSkin())
        }

        binding.leftArrowSkin.setOnClickListener{
            binding.skinIV.setImageResource(viewModel.leftArrowSkin())
        }

        binding.leftArrowSpeed.setOnClickListener{
            binding.speedTV.text = viewModel.leftArrowSpeed(screenHeight).toString()
        }

        binding.rightArrowSpeed.setOnClickListener{
            binding.speedTV.text = viewModel.rightArrowSpeed(screenHeight).toString()
        }

        return binding.root
    }

    private fun createFragment(fragment: GameFragment) {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

    private fun createFragmentBack() {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, MainFragment())
        fragmentTransaction.commit()
    }
}