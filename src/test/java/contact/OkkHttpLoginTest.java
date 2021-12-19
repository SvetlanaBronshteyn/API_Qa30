package contact;
import dto.AuthRequestDto;
import dto.ErrorDto;
import okhttp3.*;
import com.google.gson.Gson;
import dto.AuthResponseDto;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class OkkHttpLoginTest
{
    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");


    @Test
    public void loginTest() throws IOException {
        AuthRequestDto requestDto= AuthRequestDto.builder()
                .email("Lanachka555@mail.ru")
                .password("Lanachka1980")
                .build();

        Gson gson=new Gson();
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(gson.toJson(requestDto),JSON);
        Request reques = new Request.Builder()
                .url("http://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();
        Response response=client.newCall(reques).execute();

        if(response.isSuccessful()){
            String responseJson = response.body().string();

            AuthResponseDto responseDto = gson.fromJson(responseJson,AuthResponseDto.class);
            System.out.println(responseDto.getToken());
            System.out.println(response.code());
            Assert.assertTrue(response.isSuccessful());

        }else {
            System.out.println(("Response code---->"+response.code()));
            ErrorDto errorDto= gson.fromJson(response.body().string(), ErrorDto.class);
            System.out.println((errorDto.getCode()+"****"+errorDto.getMessage()+"****"+errorDto.getDetails()));



        }



    }
}
