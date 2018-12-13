package com.nakien.myweekend.util;

import android.util.Log;

import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.resource.Emailv31;
import com.nakien.myweekend.item.History;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SendEmail {
    private static final String MJ_APIKEY_PUBLIC = "07f2439bcbbc07154e88f295676c8e3b";
    private static final String MJ_APIKEY_PRIVATE = "a94ca0dbdaacf744b7ab66966a85490d";
        public static void send(History history, String email, String chair) throws MailjetException, MailjetSocketTimeoutException, JSONException {
            MailjetClient client;
            MailjetRequest request;
            MailjetResponse response;
            client = new MailjetClient(MJ_APIKEY_PUBLIC, MJ_APIKEY_PRIVATE, new ClientOptions("v3.1"));
            request = new MailjetRequest(Emailv31.resource)
                    .property(Emailv31.MESSAGES, new JSONArray()
                            .put(new JSONObject()
                                    .put(Emailv31.Message.FROM, new JSONObject()
                                            .put("Email", "myweekend.cinemas@gmail.com")
                                            .put("Name", "MW Cinemas"))
                                    .put(Emailv31.Message.TO, new JSONArray()
                                            .put(new JSONObject()
                                                    .put("Email", email)))
                                    .put(Emailv31.Message.TEMPLATEID, 606315)
                                    .put(Emailv31.Message.TEMPLATELANGUAGE, true)
                                    .put(Emailv31.Message.SUBJECT, "Thông báo đặt vé thành công!")
                                    .put(Emailv31.Message.VARIABLES, new JSONObject()
                                            .put("film_name", history.getFilmName())
                                            .put("book_date", history.getDateTimeBook())
                                            .put("cinemas_center", history.getCinemasCenter())
                                            .put("cinema", history.getCinema())
                                            .put("schedule", history.getDateTime())
                                            .put("total_ticket", chair)
                                            .put("total", Util.formatMoney(history.getTotal())+"đ")
                                    )));
            response = client.post(request);
            Log.d("SendEmail", response.getStatus()+"");
            Log.d("SendEmailData", response.getData().toString());
        }
}
