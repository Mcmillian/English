package com.mcmillian.english.business.exam

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mcmillian.english.R
import com.mcmillian.english.base.BaseFragment
import org.jetbrains.anko.support.v4.withArguments



class ExamFragment : BaseFragment() {
    private val units :ArrayList<Int> by lazy { arguments?.getIntegerArrayList(ARG_UNITS) ?: throw RuntimeException("跳转考试界面最少指定一个单元") }
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_exam, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        private const val ARG_UNITS = "units"

        @JvmStatic
        fun newInstance(param1: String, param2: String) = ExamFragment().withArguments(ARG_UNITS to param1)
    }
}
