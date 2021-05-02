package com.bike_control;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.security.cert.CertPathBuilder;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public class SendCode {
    static class NetworkService {
        //create NS instance
        private static NetworkService mInstance;
        //        private static final String BASE_URL = "http://jsonplaceholder.typicode.com";
        private static final String BASE_URL = "https://palachintosh.com/";
        private Retrofit mRetrofit;




//        public static class ReceivedCookiesInterceptor implements Interceptor {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Response originalResponse = chain.proceed(chain.request());
//                System.out.print(originalResponse.headers("Set-Cookie"));
//                return originalResponse;
//            }
//        }




        private NetworkService() {


//            OkHttpClient.Builder okHttpClient = new OkHttpClient().newBuilder();
//            okHttpClient.interceptors().add(new ReceivedCookiesInterceptor());

            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
//                    .client(okHttpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        public static NetworkService getInstance() {
            if (mInstance == null) {
                mInstance = new NetworkService();
            }
            return mInstance;
        }

        public JSONPlaceHolderApi getJSONApi() {
            return mRetrofit.create(JSONPlaceHolderApi.class);
        }

    }

    public interface JSONPlaceHolderApi {
        @POST("bikes_monitoring/mobile_app/api/") // working like decorator in python. This is call "annotation"
//        public Call<Post> getPostWithID(@Path("id") int id);
//        public Call<EnterCode.Post> requestBikeCode(@Query("code") String code);
//         Call<Post> requestBikeCode(@Body Post data);
        @FormUrlEncoded
        Call<Post> requestBikeCode(@Field("code") String code,
                                   @Field("w_from") String w_from,
                                   @Field("w_to") String w_to,
                                   @Field("quantity_to_transfer") String quantity_to_transfer);
    }

    public class Post {
        @SerializedName("code")
        @Expose
        private String code;
        public String getCode() { return code; }
        public void setCode(String code) {
            this.code = code;
        }

        @SerializedName("error")
        @Expose
        private String r_error;
        public String getError() {
            return r_error;
        }

        @SerializedName("success")
        @Expose
        private String r_success;
        public String getSuccess() {
            return r_success;
        }

        @SerializedName("typeError")
        @Expose
        private String r_TypeError;
        public String getTypeError() {
            return r_TypeError;
        }

        @SerializedName("w_from")
        @Expose
        private String w_from;
        public String getW_from() { return w_from; }
        public void setW_from(String w_from) {this.w_from = w_from;}

        @SerializedName("w_to")
        @Expose
        private String w_to;
        public String getW_to() { return w_to; }
        public void setW_to(String w_to) { this.w_to = w_to; }

        @SerializedName("quantity_to_transfer")
        @Expose
        private String quantity_to_transfer;
        public String getQuantity_to_transfer() { return quantity_to_transfer; }
        public void setQuantity_to_transfer(String quantity_to_transfer) {
            this.quantity_to_transfer = quantity_to_transfer;
        }

        @SerializedName("quantity")
        @Expose
        private String total_quantity;
        public String getTotal_quantity() { return total_quantity; }

        @SerializedName("date")
        @Expose
        private String date;
        public String getDate_time() { return date; }

        @SerializedName("name")
        @Expose
        private String name;
        public String getName() { return name; }
    }

}