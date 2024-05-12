import com.example.cookingbysteps.NotificationHelper;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Обработка входящего уведомления
        if (remoteMessage.getData().size() > 0) {
            // Обработка данных уведомления
            String title = remoteMessage.getData().get("title");
            String body = remoteMessage.getData().get("body");

            // Вывод уведомления
            NotificationHelper.displayNotification(this, title, body);
        }
    }
}
