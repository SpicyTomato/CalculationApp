package com.spicytomato.summary_test;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateVMFactory;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spicytomato.summary_test.databinding.FragmentResultBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragment extends Fragment {


    public ResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        MyViewModel myViewModel;
        myViewModel = ViewModelProviders.of(requireActivity(),new SavedStateVMFactory(requireActivity())).get(MyViewModel.class);

        FragmentResultBinding binding;
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_result,container,false);
        binding.setData(myViewModel);
        binding.setLifecycleOwner(requireActivity());



        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_resultFragment_to_homeFragment);
            }
        });

        return binding.getRoot();

        //return inflater.inflate(R.layout.fragment_result, container, false);
    }

}
