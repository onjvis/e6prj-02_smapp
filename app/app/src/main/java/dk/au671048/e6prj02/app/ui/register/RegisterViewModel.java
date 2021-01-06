package dk.au671048.e6prj02.app.ui.register;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import dk.au671048.e6prj02.app.Constant;

public class RegisterViewModel extends AndroidViewModel {
    private static final String TAG = "RegisterViewModel";

    private MutableLiveData<String> username;
    private MutableLiveData<String> password;
    private MutableLiveData<String> repeatPassword;
    private MutableLiveData<Boolean> success;

    private MutableLiveData<String> errorMessage;

    private RequestQueue queue;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        username = new MutableLiveData<>();
        password = new MutableLiveData<>();
        repeatPassword = new MutableLiveData<>();
        success = new MutableLiveData<>();
        queue = Volley.newRequestQueue(application.getApplicationContext());
    }

    public LiveData<String> getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username.setValue(username);
    }

    public LiveData<String> getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password.setValue(password);
    }

    public LiveData<String> getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword.setValue(repeatPassword);
    }

    public LiveData<Boolean> getSuccess() {
        return success;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void submitRegisterForm() {
        String postUrl = Constant.API_BASE_URL + "/auth/register";

        JSONObject postData = new JSONObject();
        try {
            postData.put(Constant.USERNAME_KEY, username.getValue());
            postData.put(Constant.PASSWORD_KEY, password.getValue());
            postData.put(Constant.ROLE_KEY, Constant.USER_VALUE);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl,
                postData, response -> {
            Log.d(TAG, "onResponse: " + response);
            submitLoginRequest();
        }, error -> error.printStackTrace());

        queue.add(jsonObjectRequest);
    }

    // After successful registration, we can login as well
    private void submitLoginRequest() {
        String postUrl = Constant.API_BASE_URL + "/auth/login";

        JSONObject postData = new JSONObject();
        try {
            postData.put(Constant.USERNAME_KEY, username.getValue());
            postData.put(Constant.PASSWORD_KEY, password.getValue());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl,
                postData, response -> {
            Log.d(TAG, "onResponse: " + response);

            SharedPreferences sp = getApplication().getApplicationContext()
                    .getSharedPreferences(Constant.SHARED_PREFS, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();

            try {
                String token = response.getString(Constant.JWT_KEY);
                editor.putString(Constant.JWT_KEY, token);
                editor.apply();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Set LiveData to true, then observe it in Fragment and switch to next fragment
            success.setValue(true);
        }, error -> error.printStackTrace());

        queue.add(jsonObjectRequest);
    }
}