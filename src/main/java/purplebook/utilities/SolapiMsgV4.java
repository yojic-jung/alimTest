package purplebook.utilities;

import com.google.firebase.messaging.Message;

import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import purplebook.model.request.MessageIds;
import purplebook.model.request.MessageList;
import purplebook.model.response.AddMessageListModel;
import purplebook.model.response.DeleteGroupModel;
import purplebook.model.response.GetMessageListModel;
import purplebook.model.response.GroupListModel;
import purplebook.model.response.GroupModel;
import purplebook.model.response.MessageModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

// 문서 : https://docs.solapi.com/rest-api-reference/message-api-v4
// 일부 API는 Query Parameter를 추가로 사용할 수 있습니다.
public interface SolapiMsgV4 {
    // 심플 메시지
    @POST("messages/v4/send")
    Call<MessageModel> sendMessage(@Header("Authorization") String auth,
                                   @Body Message message);
    // 여러건 발송
    @POST("messages/v4/send-many")
    Call<GroupModel> sendMessages(@Header("Authorization") String auth,
                                   @Body JsonObject messages);
    // 그룹 메시지 - 그룹 생성
    @POST("messages/v4/groups")
    Call<GroupModel> createGroup(@Header("Authorization") String auth);

    // 그룹 메시지 - 그룹 목록
    @GET("messages/v4/groups")
    Call<GroupListModel> getGroups(@Header("Authorization") String auth);

    // 그룹 메시지 - 그룹 정보
    @GET("messages/v4/groups/{groupId}")
    Call<GroupModel> getGroupInfo(@Header("Authorization") String auth,
                                  @Path("groupId") String groupId);

    // 그룹 메시지 - 그룹 삭제
    @DELETE("messages/v4/groups/{groupId}")
    Call<GroupModel> deleteGroupInfo(@Header("Authorization") String auth,
                                     @Path("groupId") String groupId);

    // 그룹 메시지 - 그룹 메시지 추가
    @PUT("messages/v4/groups/{groupId}/messages")
    Call<AddMessageListModel> addGroupMessage(@Header("Authorization") String auth,
                                              @Path("groupId") String groupId,
                                              @Body MessageList messages);

    // 그룹 메시지 - 그룹 메시지 발송
    @POST("messages/v4/groups/{groupId}/send")
    Call<ResponseBody> sendGroupMessage(@Header("Authorization") String auth,
                                        @Path("groupId") String groupId);

    // 그룹 메시지 - 그룹 메시지 삭제
    @HTTP(method = "DELETE", path = "messages/v4/groups/{groupId}/messages", hasBody = true)
    Call<DeleteGroupModel> deleteGroupMessages(@Header("Authorization") String auth,
                                               @Path("groupId") String groupId,
                                               @Body MessageIds messageIds);

    // 메시지 조회
    @GET("messages/v4/list")
    Call<GetMessageListModel> getMessageList(@Header("Authorization") String auth);
}
