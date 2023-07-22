package com.example.sportingbike.ui.fragment.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sportingbike.R
import com.example.sportingbike.databinding.FragmentMainBinding
import com.example.sportingbike.ui.fragment.game.GameStartFragment
import com.example.sportingbike.ui.fragment.quiz.QuizFragment

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.playBtn.setOnClickListener {
            viewModel.selectFragment(1)
        }

        binding.quizBtn.setOnClickListener {
            viewModel.selectFragment(2)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.selectedFragment.observe(viewLifecycleOwner) { fragment ->
            when (fragment) {
                1 -> showFragmentPlay()
                2 -> showFragmentQuiz()
            }
        }
    }

    private fun showFragmentPlay() {
        // Отображение Fragment 1(Play)
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, GameStartFragment())
        fragmentTransaction.commit()
    }

    private fun showFragmentQuiz() {
        // Отображение Fragment 2(Quiz)
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, QuizFragment())
        fragmentTransaction.commit()
    }
}