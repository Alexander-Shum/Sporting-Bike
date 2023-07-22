package com.example.sportingbike.ui.fragment.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sportingbike.R
import com.example.sportingbike.databinding.FragmentGameResultBinding
import com.example.sportingbike.ui.fragment.menu.MainFragment

class GameResultFragment : Fragment() {
    private lateinit var binding: FragmentGameResultBinding
    private lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameResultBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(
            requireActivity()
        )[GameViewModel::class.java]

        viewModel.saveRecordScore()
        viewModel.loadRecordScore()

        viewModel.score.observe(viewLifecycleOwner) {score ->
            binding.scoreTV.text = score.toString()
        }

        viewModel.highScore.observe(viewLifecycleOwner) {hishScore ->
            binding.recordTV.text = "Record: $hishScore"
        }

        binding.restartBtn.setOnClickListener{
            viewModel.reset()
            showFragmentGame()
        }

        binding.menuBtn.setOnClickListener{
            viewModel.reset()
            showFragmentMain()
        }

        return binding.root
    }

    private fun showFragmentMain() {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, MainFragment())
        fragmentTransaction.commit()
    }

    private fun showFragmentGame() {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, GameStartFragment())
        fragmentTransaction.commit()
    }
}