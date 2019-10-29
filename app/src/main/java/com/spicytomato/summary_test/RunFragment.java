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

import com.spicytomato.summary_test.databinding.FragmentRunBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class RunFragment extends Fragment {


    public RunFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_run, container, false);


        final MyViewModel myViewModel;
        myViewModel = ViewModelProviders.of(requireActivity(), new SavedStateVMFactory(requireActivity())).get(MyViewModel.class);

        final FragmentRunBinding binding;
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_run, container, false);
        binding.setData(myViewModel);
        binding.setLifecycleOwner(requireActivity());

        myViewModel.generateEquation();
        final StringBuilder builder = new StringBuilder();//创建一个可变字符串 字符序列


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.button0:
                        builder.append(0);
                        break;
                    case R.id.button1:
                        builder.append(1);
                        break;
                    case R.id.button2:
                        builder.append(2);
                        break;
                    case R.id.button3:
                        builder.append(3);
                        break;
                    case R.id.button4:
                        builder.append(4);
                        break;
                    case R.id.button5:
                        builder.append(5);
                        break;
                    case R.id.button6:
                        builder.append(6);
                        break;
                    case R.id.button7:
                        builder.append(7);
                        break;
                    case R.id.button8:
                        builder.append(8);
                        break;
                    case R.id.button9:
                        builder.append(9);
                        break;
                    case R.id.buttonC:
                        builder.setLength(0);
                    case R.id.buttonMinus:
                        builder.append("- ");//why？？？？？？
                    case R.id.buttonDelete:
                        if(builder.length() != 0){
                            builder.delete(builder.length()-1,builder.length());
                        }
                }
                if(builder.length() == 0){
                    binding.textView5.setText(getString(R.string.run_textView_5_input_message));
                }
                else{
                    binding.textView5.setText(builder.toString());
                }
            }
        };

        binding.button0.setOnClickListener(listener);
        binding.button1.setOnClickListener(listener);
        binding.button2.setOnClickListener(listener);
        binding.button3.setOnClickListener(listener);
        binding.button4.setOnClickListener(listener);
        binding.button5.setOnClickListener(listener);
        binding.button6.setOnClickListener(listener);
        binding.button7.setOnClickListener(listener);
        binding.button8.setOnClickListener(listener);
        binding.button9.setOnClickListener(listener);
        binding.buttonC.setOnClickListener(listener);
        binding.buttonMinus.setOnClickListener(listener);
        binding.buttonDelete.setOnClickListener(listener);

        binding.buttonCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(builder.length() == 0){
                    binding.textView5.setText(getString(R.string.run_textView_5_input_message));
                }
                else{
                    if(Integer.valueOf(builder.toString()).intValue() == myViewModel.getAnswer().getValue()) {
                        myViewModel.answerCorrect();
                        builder.setLength(0);
                        binding.textView5.setText(R.string.run_textView_5_correct_message);
                        //builder.append(getString(R.string.run_textView_5_correct_message));
                    }else{
                        NavController controller = Navigation.findNavController(v);

                        if(myViewModel.win_flag){
                            myViewModel.save();
                            myViewModel.win_flag = false;
                            controller.navigate(R.id.action_runFragment_to_resultFragment);
                        }else{
                            controller.navigate(R.id.action_runFragment_to_loseFragment);
                        }
                    }
                }
            }
        });



        return binding.getRoot();
    }

}
