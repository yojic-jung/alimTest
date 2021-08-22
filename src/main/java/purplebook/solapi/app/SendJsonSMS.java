package purplebook.solapi.app;

import java.io.IOException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import purplebook.model.response.GroupModel;
import purplebook.utilities.APIInit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 한번 요청으로 1만건까지 SMS 발송이 가능합니다.
 */
public class SendJsonSMS {
    public static void main(String[] args) {
        JsonObject params = new JsonObject();
        JsonArray messages = new JsonArray();

        JsonObject msg = new JsonObject();
        msg.addProperty("to", "01086491176");
        msg.addProperty("from", "01086491176");
        msg.addProperty("text", "콕사부 sms 문자 테스트, 12345678, ABCDEFGI, abdefhi");
        messages.add(msg);

        // ... 최대 1만건까지 추가 가능

        params.add("messages", messages);

        Call<GroupModel> api = APIInit.getAPI().sendMessages(APIInit.getHeaders(), params);
        api.enqueue(new Callback<GroupModel>() {
            @Override
            public void onResponse(Call<GroupModel> call, Response<GroupModel> response) {
                // 성공 시 200이 출력됩니다.
                if (response.isSuccessful()) {
                    System.out.println("statusCode : " + response.code());
                    GroupModel body = response.body();
                    System.out.println("groupId : " + body.getGroupId());
                    System.out.println("status: " + body.getStatus());
                    System.out.println("count: " + body.getCount().toString());
                } else {
                    try {
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GroupModel> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }
}
