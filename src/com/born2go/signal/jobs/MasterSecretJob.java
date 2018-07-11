package com.born2go.signal.jobs;

import android.content.Context;

import com.born2go.signal.crypto.MasterSecret;
import com.born2go.signal.jobmanager.JobParameters;
import com.born2go.signal.service.KeyCachingService;

public abstract class MasterSecretJob extends ContextJob {

  public MasterSecretJob(Context context, JobParameters parameters) {
    super(context, parameters);
  }

  @Override
  public void onRun() throws Exception {
    MasterSecret masterSecret = getMasterSecret();
    onRun(masterSecret);
  }

  @Override
  public boolean onShouldRetry(Exception exception) {
    if (exception instanceof RequirementNotMetException) return true;
    return onShouldRetryThrowable(exception);
  }

  public abstract void onRun(MasterSecret masterSecret) throws Exception;
  public abstract boolean onShouldRetryThrowable(Exception exception);

  private MasterSecret getMasterSecret() throws RequirementNotMetException {
    MasterSecret masterSecret = KeyCachingService.getMasterSecret(context);

    if (masterSecret == null) throw new RequirementNotMetException();
    else                      return masterSecret;
  }

  protected static class RequirementNotMetException extends Exception {}

}
