package com.bike_control;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bike_control.SendCode;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import org.w3c.dom.Text;

import dialogs.CustomDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Scan extends AppCompatActivity implements View.OnClickListener {

    // use a compound button so either checkbox or switch widgets work.
    private CompoundButton autoFocus;
    private CompoundButton useFlash;
    private TextView statusMessage;
    private TextView barcodeValue;
    public String BarcodeString = "";
    public RequestData requestData;


    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BarcodeMain";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        statusMessage = (TextView) findViewById(R.id.status_message);
        barcodeValue = (TextView) findViewById(R.id.barcode_value);

        autoFocus = (CompoundButton) findViewById(R.id.auto_focus);
        useFlash = (CompoundButton) findViewById(R.id.use_flash);

        findViewById(R.id.read_barcode).setOnClickListener((View.OnClickListener) this);


        Bundle data_vars = getIntent().getExtras();

        if (data_vars != null) {
            requestData = (RequestData) data_vars.getSerializable(RequestData.class.getSimpleName());
        }
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.read_barcode) {
            // launch barcode activity.
            Intent intent = new Intent(this, BarcodeCaptureActivity.class);
            intent.putExtra(BarcodeCaptureActivity.AutoFocus, autoFocus.isChecked());
            intent.putExtra(BarcodeCaptureActivity.UseFlash, useFlash.isChecked());

            startActivityForResult(intent, RC_BARCODE_CAPTURE);
        }

    }

    /**
     * Called when an activity you launched exits, giving you the requestCode
     * you started it with, the resultCode it returned, and any additional
     * data from it.  The <var>resultCode</var> will be
     * {@link #RESULT_CANCELED} if the activity explicitly returned that,
     * didn't return any result, or crashed during its operation.
     * <p/>
     * <p>You will receive this call immediately before onResume() when your
     * activity is re-starting.
     * <p/>
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode  The integer result code returned by the child activity
     *                    through its setResult().
     * @param data        An Intent, which can return result data to the caller
     *                    (various data can be attached to Intent "extras").
     * @see #startActivityForResult
     * @see #createPendingResult
     * @see #setResult(int)
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    statusMessage.setText(R.string.barcode_success);
                    barcodeValue.setText(barcode.displayValue);

                    BarcodeString = barcode.displayValue;

                    Log.d(TAG, "Barcode read: " + barcode.displayValue);

//                    requestSend(barcode.displayValue);

                } else {
                    statusMessage.setText(R.string.barcode_failure);
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                statusMessage.setText(String.format(getString(R.string.barcode_error),
                        CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void sendBarcode(View view) {
        requestSend();
    }

    public void requestSend() {
        TextView textView = (TextView) findViewById(R.id.barcode_value);

//        CustomDialog dialog = new CustomDialog();
//        Bundle bundle = new Bundle();

        CustomDialog dialog = new CustomDialog();
        Bundle bundle = new Bundle();

        SendCode.NetworkService.getInstance()
                .getJSONApi()
                //.getPostWithID(con_val)
                .requestBikeCode( BarcodeString,
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
                                textView.append("Is this code " + BarcodeString + " valid?");
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
    }

}