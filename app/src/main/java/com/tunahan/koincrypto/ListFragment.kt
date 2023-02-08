package com.tunahan.koincrypto

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.tunahan.koincrypto.databinding.FragmentListBinding
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

class ListFragment : Fragment(), CryptoAdapter.Listener, AndroidScopeComponent {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private var cryptoAdapter = CryptoAdapter(arrayListOf(), this)
    private val viewModel by viewModel<CryptoViewModel>()

    /* constructor injection kullanmayacaksak

    private val api = get<CryptoAPI>()
    private val apilazy by inject<CryptoAPI>()
    */

    override val scope: Scope by fragmentScope()
    private val hello by inject<String>(qualifier = named("hello"))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        viewModel.getDataFromAPI()

        observeLiveData()

    }

    private fun observeLiveData() {
        viewModel.cryptoList.observe(viewLifecycleOwner, Observer { cryptos ->
            cryptos?.let {
                binding.recyclerView.visibility = View.VISIBLE
                cryptoAdapter =
                    CryptoAdapter(ArrayList(cryptos.data)?: arrayListOf(), this@ListFragment)
                binding.recyclerView.adapter = cryptoAdapter
            }

        })

        viewModel.cryptoError.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if (it.data == true) {
                    binding.cryptoErrorText.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                } else {
                    binding.cryptoErrorText.visibility = View.GONE
                }
            }

        })

        viewModel.cryptoLoading.observe(viewLifecycleOwner, Observer { l ->
            l?.let {
                if (it.data == true) {
                    binding.cryptoProgressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    binding.cryptoErrorText.visibility = View.GONE
                } else {
                    binding.cryptoProgressBar.visibility = View.GONE
                }
            }

        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    override fun onItemClick(cryptoModel: Crypto) {
        Toast.makeText(requireContext(), "Clicked on: ${cryptoModel.currency}", Toast.LENGTH_LONG)
            .show()
    }



}