/**
 * Created by Jerem on 9/24/2017.
 */

import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobile.auth.core.IdentityManager;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager;
import com.amazonaws.mobileconnectors.pinpoint.PinpointConfiguration;

/**
 * Application class responsible for initializing singletons and other common components.
 */
public class Application extends MultiDexApplication {
    private static final String LOG_TAG = Application.class.getSimpleName();

    public static PinpointManager pinpointManager;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeApplication();

    }

    private void initializeApplication() {

        AWSConfiguration awsConfig = new AWSConfiguration(getApplicationContext());

        // If IdentityManager is not created, create it
        if (IdentityManager.getDefaultIdentityManager() == null) {
            AWSConfiguration awsConfiguration =
                    new AWSConfiguration(getApplicationContext());
            IdentityManager identityManager =
                    new IdentityManager(getApplicationContext(), awsConfiguration);
            IdentityManager.setDefaultIdentityManager(identityManager);
        }

        // Register identity providers here.
        // With none registered IdentityManager gets unauthenticated AWS credentials


        final AWSCredentialsProvider credentialsProvider =
                IdentityManager.getDefaultIdentityManager().getCredentialsProvider();

        PinpointConfiguration pinpointConfig = new PinpointConfiguration(
                getApplicationContext(),
                credentialsProvider,
                awsConfig);

        Application.pinpointManager = new PinpointManager(pinpointConfig);

        pinpointManager.getSessionClient().startSession();


        // Choose a meaningful point in your apps lifecycle to mark the end of your session
         pinpointManager.getSessionClient().stopSession();
         pinpointManager.getAnalyticsClient().submitEvents();

    }

}