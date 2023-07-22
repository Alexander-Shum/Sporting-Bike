package com.example.sportingbike.ui.fragment.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sportingbike.R
import com.example.sportingbike.databinding.FragmentOnBoardingBinding

class OnBoardingFragment : Fragment() {
    private lateinit var binding: FragmentOnBoardingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnBoardingBinding.inflate(inflater, container, false)

        initAdapter()

        return binding.root
    }

    private fun initAdapter(){
        binding.viewpager.adapter = CustomPagerAdapter(requireContext(), requireActivity())
        binding.wormDotsIndicator.setViewPager(binding.viewpager)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}