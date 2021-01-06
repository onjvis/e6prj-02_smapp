package dk.au671048.e6prj02.app.ui.register;

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

import com.google.android.material.textfield.TextInputLayout;

import dk.au671048.e6prj02.app.R;

public class RegisterFragment extends Fragment {
    private static final String TAG = "RegisterFragment";

    private RegisterViewModel viewModel;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    private TextInputLayout passwordInputLayout, repeatPasswordInputLayout;
    private TextView username, password, repeatPassword;
    private Button cancel, submit;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.register_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        passwordInputLayout = view.findViewById(R.id.register_password_input_layout);
        repeatPasswordInputLayout = view.findViewById(R.id.register_repeat_password_input_layout);

        username = view.findViewById(R.id.register_username);
        password = view.findViewById(R.id.register_password);

        password.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                validatePasswords();
            }
        });

        repeatPassword = view.findViewById(R.id.register_repeat_password);

        repeatPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                validatePasswords();
            }
        });

        cancel = view.findViewById(R.id.register_cancel);
        submit = view.findViewById(R.id.register_submit);

        viewModel.getUsername().observe(getViewLifecycleOwner(), s -> username.setText(s));

        viewModel.getPassword().observe(getViewLifecycleOwner(), s -> password.setText(s));

        viewModel.getRepeatPassword().observe(getViewLifecycleOwner(),
                s -> repeatPassword.setText(s));

        cancel.setOnClickListener(v -> getActivity().onBackPressed());

        submit.setOnClickListener(v -> submitRegisterForm());

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

    private void navigateToApp() {
        NavHostFragment.findNavController(this).navigate(R.id.registerToWarningList);
    }

    private void submitRegisterForm() {
        viewModel.setUsername(username.getText().toString());
        viewModel.setPassword(password.getText().toString());
        viewModel.setRepeatPassword(repeatPassword.getText().toString());

        viewModel.submitRegisterForm();
    }

    private void validatePasswords() {
        if (!repeatPassword.getText().toString().equals(password.getText().toString())) {
            repeatPasswordInputLayout.setError(getResources().getString(R.string.error_passwords_match));
        } else {
            repeatPasswordInputLayout.setError(null);
        }
    }
}