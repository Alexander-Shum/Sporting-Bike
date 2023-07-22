package com.example.sportingbike.ui.fragment.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sportingbike.R
import com.example.sportingbike.databinding.FragmentResultBinding
import com.example.sportingbike.ui.fragment.menu.MainFragment

class ResultFragment : Fragment() {
    private lateinit var binding: FragmentResultBinding
    private lateinit var viewModel: QuizViewModel
    private lateinit var viewModelResult: ResultViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(requireActivity())[QuizViewModel::class.java]
        viewModelResult = ViewModelProvider(this)[ResultViewModel::class.java]

        viewModel.saveData()
        viewModel.loadData()

        viewModel.score.observe(viewLifecycleOwner) {score ->
            binding.scoreTV.text = "Result: " + score.toString()
        }

        viewModel.highScore.observe(viewLifecycleOwner) {score ->
            binding.recordTV.text = " Record: " + score.toString()
        }

        binding.restartBtn.setOnClickListener{
            viewModelResult.selectFragment(0)
        }

        binding.menuBtn.setOnClickListener{
            viewModelResult.selectFragment(1)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelResult.selectedFragment.observe(viewLifecycleOwner) {fragment ->
            when(fragment){
                0 -> showFragmentRestart()
                1 -> showFragmentMenu()
            }
        }
    }

    private fun showFragmentMenu() {
        viewModel.reset()
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, MainFragment())
        fragmentTransaction.commit()
    }

    private fun showFragmentRestart() {
        viewModel.reset()
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, QuizFragment())
        fragmentTransaction.commit()
    }
}