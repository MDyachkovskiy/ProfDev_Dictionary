package gb.com.view.image

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import gb.com.R
import gb.com.databinding.FragmentImageBinding
import gb.com.model.data.wordDefinition.AppState
import gb.com.model.data.wordImage.SkyengWord
import gb.com.view.alertDialog.AlertDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ImageFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentImageBinding? = null
    private val binding get() = _binding!!

    lateinit var model: ImageViewModel

    companion object {
        private const val ARG_WORD_TAG = "word_bundle"
        private const val ARG_PART_OF_SPEECH_TAG = "part_of_speech_bundle"
        private const val DIALOG_FRAGMENT_TAG = "74a54328-5d62-46bf-ab6b-cbf5d8c79522"

        fun newInstance(word: String, partOfSpeech: String) : ImageFragment {
            val fragment = ImageFragment()
            val args = Bundle()
            args.putString(ARG_WORD_TAG, word)
            args.putString(ARG_PART_OF_SPEECH_TAG, partOfSpeech)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageBinding.inflate(inflater, container, false)

        initViewModel()
        initView()

        return binding.root
    }

    private fun initView() {
        val word = arguments?.getString(ARG_WORD_TAG)
        word?.let{model.getData(word, true)}
        val partOfSpeech = arguments?.getString(ARG_PART_OF_SPEECH_TAG)
        with(binding){
            pictureHeader.text = word
            picturePartOfSpeech.text = partOfSpeech
        }
    }

    private fun setupImage(imageUrl: String?){
        val imageView = binding.pictureImageview
        val loadingLayout: View = binding.loadingLayout
        if(imageUrl != null){
            Glide.with(imageView)
                .load("https:$imageUrl")
                .listener(object : RequestListener<Drawable> {

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        loadingLayout.visibility = View.GONE
                        imageView.setImageResource(R.drawable.ic_load_error_vector)
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        loadingLayout.visibility = View.GONE
                        return false
                    }
                })
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.no_photo_vector)
                        .centerCrop()
                )
                .into(imageView)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.closeButton.setOnClickListener { dismiss() }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initViewModel() {
        val viewModel: ImageViewModel by viewModel()
        model = viewModel
        lifecycleScope.launchWhenStarted{
            model.stateFlow.collect { renderData(it) }
        }
    }

    private fun renderData(appState: AppState) {
        when(appState) {
            is AppState.Success<*> -> {
                val data = appState.data as? List<SkyengWord>
                data?.let {
                    if (it.isEmpty()) {
                        showAlertDialog(
                            getString(R.string.title_no_definitions),
                            getString(R.string.error_no_definitions)
                        )
                    } else {
                        val imageUrl = it[0].meanings[0].imageUrl
                        setupImage(imageUrl)
                    }
                }
            }
            is AppState.Error -> {
                showAlertDialog(getString(R.string.dialog_title_stub), appState.error.message)
            }
            is AppState.Loading -> {
                // do nothing
            }
        }
    }

    private fun showAlertDialog(title: String?, message: String?) {
        AlertDialogFragment.newInstance(title, message)
            .show(parentFragmentManager, DIALOG_FRAGMENT_TAG)
    }
}