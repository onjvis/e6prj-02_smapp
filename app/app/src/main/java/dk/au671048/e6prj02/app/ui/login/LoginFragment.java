package dk.au671048.e6prj02.app.ui.login;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import dk.au671048.e6prj02.app.R;

public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";
    private LoginViewModel viewModel;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    private TextView username, password;
    private Button register, submit;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        username = view.findViewById(R.id.login_username);
        password = view.findViewById(R.id.login_password);
        register = view.findViewById(R.id.login_register);
        submit = view.findViewById(R.id.login_submit);

        viewModel.getUsername().observe(getViewLifecycleOwner(), s -> username.setText(s));

        viewModel.getPassword().observe(getViewLifecycleOwner(), s -> password.setText(s));

        register.setOnClickListener(v -> navigateToRegister());

        submit.setOnClickListener(v -> submitLoginForm());

        viewModel.getSuccess().observe(getViewLifecycleOwner(), success -> {
            if (success) {
                navigateToApp();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.INVISIBLE);
    }

    private void navigateToRegister() {
        NavHostFragment.findNavController(this).navigate(R.id.loginToRegister);
    }

    private void navigateToApp() {
        NavHostFragment.findNavController(this).navigate(R.id.loginToWarningList);
    }

    private void submitLoginForm() {
        viewModel.setUsername(username.getText().toString());
        viewModel.setPassword(password.getText().toString());

        viewModel.submitLoginForm();
    }
}