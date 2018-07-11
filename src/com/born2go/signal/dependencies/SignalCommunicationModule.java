package com.born2go.signal.dependencies;

import android.content.Context;
import android.util.Log;

import com.born2go.signal.CreateProfileActivity;
import com.born2go.signal.DeviceListFragment;
import com.born2go.signal.crypto.storage.SignalProtocolStoreImpl;
import com.born2go.signal.events.ReminderUpdateEvent;
import com.born2go.signal.jobs.AttachmentDownloadJob;
import com.born2go.signal.jobs.AvatarDownloadJob;
import com.born2go.signal.jobs.CleanPreKeysJob;
import com.born2go.signal.jobs.CreateSignedPreKeyJob;
import com.born2go.signal.jobs.GcmRefreshJob;
import com.born2go.signal.jobs.MultiDeviceBlockedUpdateJob;
import com.born2go.signal.jobs.MultiDeviceContactUpdateJob;
import com.born2go.signal.jobs.MultiDeviceGroupUpdateJob;
import com.born2go.signal.jobs.MultiDeviceProfileKeyUpdateJob;
import com.born2go.signal.jobs.MultiDeviceReadReceiptUpdateJob;
import com.born2go.signal.jobs.MultiDeviceReadUpdateJob;
import com.born2go.signal.jobs.MultiDeviceVerifiedUpdateJob;
import com.born2go.signal.jobs.PushGroupSendJob;
import com.born2go.signal.jobs.PushGroupUpdateJob;
import com.born2go.signal.jobs.PushMediaSendJob;
import com.born2go.signal.jobs.PushNotificationReceiveJob;
import com.born2go.signal.jobs.PushTextSendJob;
import com.born2go.signal.jobs.RefreshAttributesJob;
import com.born2go.signal.jobs.RefreshPreKeysJob;
import com.born2go.signal.jobs.RequestGroupInfoJob;
import com.born2go.signal.jobs.RetrieveProfileAvatarJob;
import com.born2go.signal.jobs.RetrieveProfileJob;
import com.born2go.signal.jobs.RotateSignedPreKeyJob;
import com.born2go.signal.jobs.SendReadReceiptJob;
import com.born2go.signal.preferences.AppProtectionPreferenceFragment;
import com.born2go.signal.push.SecurityEventListener;
import com.born2go.signal.push.SignalServiceNetworkAccess;
import com.born2go.signal.service.MessageRetrievalService;
import com.born2go.signal.service.WebRtcCallService;
import com.born2go.signal.util.TextSecurePreferences;

import org.greenrobot.eventbus.EventBus;
import com.born2go.signal.BuildConfig;
import org.whispersystems.libsignal.util.guava.Optional;
import org.whispersystems.signalservice.api.SignalServiceAccountManager;
import org.whispersystems.signalservice.api.SignalServiceMessageReceiver;
import org.whispersystems.signalservice.api.SignalServiceMessageSender;
import org.whispersystems.signalservice.api.util.CredentialsProvider;
import org.whispersystems.signalservice.api.websocket.ConnectivityListener;

import dagger.Module;
import dagger.Provides;

@Module(complete = false, injects = {CleanPreKeysJob.class,
                                     CreateSignedPreKeyJob.class,
                                     PushGroupSendJob.class,
                                     PushTextSendJob.class,
                                     PushMediaSendJob.class,
                                     AttachmentDownloadJob.class,
                                     RefreshPreKeysJob.class,
                                     MessageRetrievalService.class,
                                     PushNotificationReceiveJob.class,
                                     MultiDeviceContactUpdateJob.class,
                                     MultiDeviceGroupUpdateJob.class,
                                     MultiDeviceReadUpdateJob.class,
                                     MultiDeviceBlockedUpdateJob.class,
                                     DeviceListFragment.class,
                                     RefreshAttributesJob.class,
                                     GcmRefreshJob.class,
                                     RequestGroupInfoJob.class,
                                     PushGroupUpdateJob.class,
                                     AvatarDownloadJob.class,
                                     RotateSignedPreKeyJob.class,
                                     WebRtcCallService.class,
                                     RetrieveProfileJob.class,
                                     MultiDeviceVerifiedUpdateJob.class,
                                     CreateProfileActivity.class,
                                     RetrieveProfileAvatarJob.class,
                                     MultiDeviceProfileKeyUpdateJob.class,
                                     SendReadReceiptJob.class,
                                     MultiDeviceReadReceiptUpdateJob.class,
                                     AppProtectionPreferenceFragment.class})
public class SignalCommunicationModule {

  private static final String TAG = SignalCommunicationModule.class.getSimpleName();

  private final Context                      context;
  private final SignalServiceNetworkAccess networkAccess;

  private SignalServiceAccountManager  accountManager;
  private SignalServiceMessageSender   messageSender;
  private SignalServiceMessageReceiver messageReceiver;

  public SignalCommunicationModule(Context context, SignalServiceNetworkAccess networkAccess) {
    this.context       = context;
    this.networkAccess = networkAccess;
  }

  @Provides
  synchronized SignalServiceAccountManager provideSignalAccountManager() {
    if (this.accountManager == null) {
      this.accountManager = new SignalServiceAccountManager(networkAccess.getConfiguration(context),
                                                            new DynamicCredentialsProvider(context),
                                                            BuildConfig.USER_AGENT);
    }

    return this.accountManager;
  }

  @Provides
  synchronized SignalServiceMessageSender provideSignalMessageSender() {
    if (this.messageSender == null) {
      this.messageSender = new SignalServiceMessageSender(networkAccess.getConfiguration(context),
                                                          new DynamicCredentialsProvider(context),
                                                          new SignalProtocolStoreImpl(context),
                                                          BuildConfig.USER_AGENT,
                                                          Optional.fromNullable(MessageRetrievalService.getPipe()),
                                                          Optional.of(new SecurityEventListener(context)));
    } else {
      this.messageSender.setMessagePipe(MessageRetrievalService.getPipe());
    }

    return this.messageSender;
  }

  @Provides
  synchronized SignalServiceMessageReceiver provideSignalMessageReceiver() {
    if (this.messageReceiver == null) {
      this.messageReceiver = new SignalServiceMessageReceiver(networkAccess.getConfiguration(context),
                                                              new DynamicCredentialsProvider(context),
                                                              BuildConfig.USER_AGENT,
                                                              new PipeConnectivityListener());
    }

    return this.messageReceiver;
  }

  private static class DynamicCredentialsProvider implements CredentialsProvider {

    private final Context context;

    private DynamicCredentialsProvider(Context context) {
      this.context = context.getApplicationContext();
    }

    @Override
    public String getUser() {
      return TextSecurePreferences.getLocalNumber(context);
    }

    @Override
    public String getPassword() {
      return TextSecurePreferences.getPushServerPassword(context);
    }

    @Override
    public String getSignalingKey() {
      return TextSecurePreferences.getSignalingKey(context);
    }
  }

  private class PipeConnectivityListener implements ConnectivityListener {

    @Override
    public void onConnected() {
      Log.w(TAG, "onConnected()");
    }

    @Override
    public void onConnecting() {
      Log.w(TAG, "onConnecting()");
    }

    @Override
    public void onDisconnected() {
      Log.w(TAG, "onDisconnected()");
    }

    @Override
    public void onAuthenticationFailure() {
      Log.w(TAG, "onAuthenticationFailure()");
      TextSecurePreferences.setUnauthorizedReceived(context, true);
      EventBus.getDefault().post(new ReminderUpdateEvent());
    }

  }

}
