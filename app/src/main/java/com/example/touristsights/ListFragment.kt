package com.example.touristsights

import android.content.res.Configuration
import android.os.Bundle
import android.provider.Settings.Global.putInt
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.touristsights.databinding.FragmentListBinding


class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.apply {
            layoutManager =
                when {
                    // 画面が縦向きならLinearLayoutManagerで表示
                    resources.configuration.orientation
                            == Configuration.ORIENTATION_PORTRAIT
                    -> LinearLayoutManager(context)
                    else
                        // 横向きなら、2列のGridを表示
                    -> GridLayoutManager(context, 2)
                }
            adapter = SightAdapter(context, getSights(resources)).apply {
                setOnItemClickListener { position: Int ->
                    fragmentManager?.let { manager: FragmentManager ->
                        val tag = "DetailFragment"
                        var fragment = manager.findFragmentByTag(tag)
                        if (fragment == null) {
                            fragment = DetailFragment()
                            fragment.arguments = Bundle().apply {
                                putInt(ROW_POSITION, position)
                            }
                            manager.beginTransaction().apply {
                                replace(R.id.content, fragment, tag)
                                addToBackStack(null)
                            }.commit()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}