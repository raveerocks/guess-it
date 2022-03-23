package io.raveerocks.guessit.screens.game

import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import io.raveerocks.guessit.R
import io.raveerocks.guessit.databinding.GameFragmentBinding
import io.raveerocks.guessit.screens.game.GameFragmentDirections.actionGameToScore


class GameFragment : Fragment() {

    private lateinit var viewModel: GameViewModel
    private lateinit var binding: GameFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        viewModel.eventGameFinish.observe(this.viewLifecycleOwner) { hasFinished ->
            run {
                if (hasFinished) {
                    gameFinished()
                }
            }
        }
        viewModel.eventBuzz.observe(viewLifecycleOwner) { buzzType ->
            if (buzzType != GameViewModel.BuzzType.NO_BUZZ) {
                buzz(buzzType.pattern)
                viewModel.onBuzzComplete()
            }
        }

        binding = DataBindingUtil.inflate(inflater, R.layout.game_fragment, container, false)
        binding.lifecycleOwner = this
        binding.gameViewModel = viewModel

        return binding.root

    }

    private fun gameFinished() {
        val currentScore = viewModel.score.value ?: 0
        val action = actionGameToScore(currentScore)
        findNavController().navigate(action)
        viewModel.onGameFinishComplete()
    }


    private fun buzz(pattern: LongArray) {
        val buzzer = activity?.getSystemService<Vibrator>()
        buzzer?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                buzzer.vibrate(VibrationEffect.createWaveform(pattern, -1))
            } else {
                //deprecated in API 26
                buzzer.vibrate(pattern, -1)
            }
        }
    }

}
