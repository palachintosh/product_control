package com.bike_control;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import dialogs.CustomDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class EnterCode extends AppCompatActivity {

    public RequestData requestData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_code);

//        RequestData requestData;
        Bundle data_vars = getIntent().getExtras();
        if (data_vars != null) {
            requestData = (RequestData) data_vars.getSerializable(RequestData.class.getSimpleName());
        }

    }


    public void sendECode(View view) {
        EditText editFieldValue = (EditText) findViewById(R.id.sendEnteredCode);
        String EDIT_TEXT_VALUE = editFieldValue.getText().toString();
//        Byte con_val = new Byte(EDIT_TEXT_VALUE);
        TextView textView = (TextView) findViewById(R.id.respose_text);

        CustomDialog dialog = new CustomDialog();
        Bundle bundle = new Bundle();

        SendCode.NetworkService.getInstance()
                .getJSONApi()
                //.getPostWithID(con_val)
                .requestBikeCode( EDIT_TEXT_VALUE,
                        requestData.getFrom_warehouse(),
                        requestData.getTo_warehouse(),
                        requestData.getQuantity_tt())
                .enqueue(new Callback<SendCode.Post>() {
                    @Override
                    public void onResponse(@NonNull Call<SendCode.Post> call, @NonNull Response<SendCode.Post> response) {
                        SendCode.Post post = response.body();
//                        Response post_raw = response.raw();
//                        okhttp3.Response post = response.raw();

                        System.out.print(post);
                        if (post != null) {
                            if (post.getSuccess() != null) {
                                textView.append(post.getSuccess() + "\n\n");
                                textView.setText("All data has been updated! \n\n");
                                editFieldValue.setText("");

                                bundle.putString("Success", post.getSuccess());
                                dialog.setArguments(bundle);
                                dialog.show(getSupportFragmentManager(), "custom");
                            }

                            if (post.getError() != null) {
                                textView.append("Product does not exist or not available on stocks!" + "\n\n");
                                textView.append(post.getError());

                                bundle.putString("Error", post.getError());
                                dialog.setArguments(bundle);
                                dialog.show(getSupportFragmentManager(), "custom");
                            }

                            if (post.getTypeError() != null) {
                                textView.append("Looks like we have a little trouble." + "\n\n");
                                textView.append("Is this code " + EDIT_TEXT_VALUE + " valid?");
                                textView.append(post.getTypeError());

                                bundle.putString("Error", post.getTypeError());
                                dialog.setArguments(bundle);
                                dialog.show(getSupportFragmentManager(), "custom");
                            }
                        } else {
                            okhttp3.Response post_data = response.raw();
                            textView.append("Unexpected error! Try again." + post_data);
                            dialog.show(getSupportFragmentManager(), "custom");
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<SendCode.Post> call, @NonNull Throwable t) {
                        textView.append("Error occurred while getting request!");
                        t.printStackTrace();
                        System.out.print(t);
                    }
                });
        //call get request handler
//        send_get_request(con_val);
    }

}