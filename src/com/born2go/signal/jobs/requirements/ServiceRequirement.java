package com.born2go.signal.jobs.requirements;

import android.content.Context;

import com.born2go.signal.jobmanager.dependencies.ContextDependent;
import com.born2go.signal.jobmanager.requirements.SimpleRequirement;
import com.born2go.signal.sms.TelephonyServiceState;

public class ServiceRequirement extends SimpleRequirement implements ContextDependent {

  private static final String TAG = ServiceRequirement.class.getSimpleName();

  private transient Context context;

  public ServiceRequirement(Context context) {
    this.context  = context;
  }

  @Override
  public void setContext(Context context) {
    this.context = context;
  }

  @Override
  public boolean isPresent() {
    TelephonyServiceState telephonyServiceState = new TelephonyServiceState();
    return telephonyServiceState.isConnected(context);
  }
}
