package com.example.sportingbike.ui.fragment.game

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sportingbike.R
import com.example.sportingbike.databinding.FragmentGameBinding
import com.example.sportingbike.utils.Constants

class GameFragment : Fragment() {
    private lateinit var binding: FragmentGameBinding
    private lateinit var viewModel: GameViewModel
    private lateinit var playerImageView: ImageView
    private lateinit var coinImageView: ImageView
    private lateinit var rockImageView: ImageView

    var marginLayoutParamsRock: ViewGroup.MarginLayoutParams? = null
    var marginLayoutParamsCoin: ViewGroup.MarginLayoutParams? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameBinding.inflate(inflater, container, false)

        val windowManager =
            requireActivity().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels

        val skinIndex = arguments?.getInt(Constants.INDEX_SKIN) as Int
        val speed = arguments?.getInt(Constants.INDEX_SPEED) as Int

        playerImageView = ImageView(requireContext())
        playerImageView.setImageResource(skinIndex )
        val layoutParamsPlayer = FrameLayout.LayoutParams(screenWidth/5, screenHeight/5)
        playerImageView.layoutParams = layoutParamsPlayer

        var marginLayoutParamsPlayer: ViewGroup.MarginLayoutParams? = playerImageView.layoutParams as ViewGroup.MarginLayoutParams
        marginLayoutParamsPlayer?.setMargins(0, 0, 0, 200)
        playerImageView.y = (screenHeight - 450).toFloat()
        playerImageView.layoutParams = marginLayoutParamsPlayer

        viewModel = ViewModelProvider(
            requireActivity(),
            GameViewModelFactory(requireActivity().application,screenHeight, screenWidth, playerImageView.height, playerImageView.width)
        )[GameViewModel::class.java]

        viewModel.initSpeed(speed)

        animateImage(binding.startLineIV, 2500)

        rockImageView = ImageView(requireContext())
        rockImageView.setImageResource(R.drawable.rock1)
        val layoutParamsRock = FrameLayout.LayoutParams(screenWidth/10, screenWidth/10)
        rockImageView.layoutParams = layoutParamsRock

        viewModel.rockX.observe(viewLifecycleOwner) { rockX ->
            viewModel.rockY.observe(viewLifecycleOwner) { rockY ->
                marginLayoutParamsRock = rockImageView.layoutParams as ViewGroup.MarginLayoutParams
                marginLayoutParamsRock?.setMargins(rockX, rockY, 0, 0)
                rockImageView.layoutParams = marginLayoutParamsRock
            }
        }
        binding.root.addView(rockImageView)

        //Add Image Coin
        coinImageView = ImageView(requireContext())
        coinImageView.setImageResource(R.drawable.coin)
        val layoutParamsCoin = FrameLayout.LayoutParams(screenWidth/10, screenWidth/10)
        coinImageView.layoutParams = layoutParamsCoin

        viewModel.coinX.observe(viewLifecycleOwner) { coinX ->
            viewModel.coinY.observe(viewLifecycleOwner) { coinY ->
                marginLayoutParamsCoin = coinImageView.layoutParams as ViewGroup.MarginLayoutParams
                marginLayoutParamsCoin?.setMargins(coinX, coinY, 0, 0)
                coinImageView.layoutParams = marginLayoutParamsCoin
            }
        }
        binding.root.addView(coinImageView)

        //Add Image Player
        viewModel.playerX.observe(viewLifecycleOwner) { playerX ->
            viewModel.playerY.observe(viewLifecycleOwner) { playerY ->
                playerImageView.x = playerX.toFloat()
                playerImageView.y = playerY.toFloat()
            }
        }
        binding.root.addView(playerImageView)

        binding.leftBtn.setOnClickListener {
            viewModel.movePlayerLeft(binding.root.height)
        }

        binding.rightBtn.setOnClickListener {
            viewModel.movePlayerRight(
                binding.root.height,
                binding.root.width,
                playerImageView.width
            )
        }

        binding.root.postDelayed(object : Runnable {
            override fun run() {
                val view = view ?: return
                val lifecycleOwner = viewLifecycleOwner ?: return
                viewModel.updatePositions(
                    coinImageView.height,
                    coinImageView.width,
                    rockImageView.height,
                    rockImageView.width,
                    binding.root.height,
                    binding.root.width
                )

                viewModel.gameOver.observe(viewLifecycleOwner) { isOver ->
                    if (isOver){
                        showFragmentResult()
                    }

                }

                // Повторение задачи через определенный интервал времени
                binding.root.postDelayed(this, 50)
            }
        }, 50)

        viewModel.score.observe(viewLifecycleOwner) {score ->
            binding.moneyTV.text = score.toString()
        }

        viewModel.health.observe(viewLifecycleOwner) {health ->
            when(health){
                3 -> binding.healthIV.setImageResource(R.drawable.health_3)
                2 -> binding.healthIV.setImageResource(R.drawable.health_2)
                1 -> binding.healthIV.setImageResource(R.drawable.health_1)
                0 -> binding.healthIV.setImageResource(R.drawable.health_0)
            }
        }

        return binding.root
    }

    private fun showFragmentResult() {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, GameResultFragment())
        fragmentTransaction.commit()
    }

    fun animateImage(view: View, duration: Long) {
        val screenHeight = view.resources.displayMetrics.heightPixels.toFloat()
        val imageHeight = view.height.toFloat()

        view.animate()
            .translationYBy(screenHeight + imageHeight)
            .setDuration(duration)
            .setInterpolator(LinearInterpolator())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    // Reset image position to the top of the screen
                    view.translationY = -imageHeight
                    binding.startLineIV.visibility = View.GONE
                }
            })
            .start()
    }
}
