package com.example.sportingbike.ui.fragment.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sportingbike.R
import com.example.sportingbike.databinding.FragmentQuizBinding
import com.example.sportingbike.ui.fragment.menu.MainFragment

class QuizFragment : Fragment() {
    private lateinit var binding: FragmentQuizBinding
    private lateinit var viewModel: QuizViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(requireActivity())[QuizViewModel::class.java]

        visibilityNextBtn(false)
        binding.nextBtn.visibility = View.GONE

        viewModel.currentQuestion.observe(viewLifecycleOwner) { question ->
            binding.questionTV.text = question?.text
        }

        viewModel.answers.observe(viewLifecycleOwner) { answers ->
            if(answers != null){
                binding.var1Btn.text = answers[0]
                binding.var2Btn.text = answers[1]
                binding.var3Btn.text = answers[2]
            }
        }

        viewModel.selectedAnswer.observe(viewLifecycleOwner) { index ->
            if (index != null) {
                binding.var1Btn.isEnabled = false
                binding.var2Btn.isEnabled = false
                binding.var3Btn.isEnabled = false
                visibilityNextBtn(true)
            } else {
                binding.var1Btn.isEnabled = true
                binding.var2Btn.isEnabled = true
                binding.var3Btn.isEnabled = true
            }
        }

        viewModel.answerCorrect.observe(viewLifecycleOwner) { isCorrect ->
            if (isCorrect == true) {
            } else {
            }
        }

        viewModel.answer1BackgroundColor.observe(viewLifecycleOwner) { colorStateList ->
            binding.var1Btn.backgroundTintList = colorStateList
        }

        viewModel.answer2BackgroundColor.observe(viewLifecycleOwner) { colorStateList ->
            binding.var2Btn.backgroundTintList = colorStateList
        }

        viewModel.answer3BackgroundColor.observe(viewLifecycleOwner) { colorStateList ->
            binding.var3Btn.backgroundTintList = colorStateList
        }

        viewModel.isGameFinished.observe(viewLifecycleOwner) { isFinished ->
            if (isFinished != null && isFinished) {
               createResultFragment()
            }
        }

        binding.nextBtn.setOnClickListener {
            viewModel.onNextQuestion()
            visibilityNextBtn(false)
        }

        binding.var1Btn.setOnClickListener {
            viewModel.onAnswerSelected(0, requireContext())
        }

        binding.var2Btn.setOnClickListener {
            viewModel.onAnswerSelected(1, requireContext())
        }

        binding.var3Btn.setOnClickListener {
            viewModel.onAnswerSelected(2, requireContext())
        }

        binding.backBtn.setOnClickListener{
            viewModel.reset()
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout, MainFragment())
            fragmentTransaction.commit()
        }

        return binding.root
    }

    fun createResultFragment() {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, ResultFragment())
        fragmentTransaction.commit()
    }

    fun visibilityNextBtn(flag: Boolean) {
        if (flag)
            binding.nextBtn.visibility = View.VISIBLE
        else
            binding.nextBtn.visibility = View.GONE
    }
}