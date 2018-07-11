package com.born2go.signal.jobs.requirements;

import android.content.Context;

import com.born2go.signal.jobmanager.dependencies.ContextDependent;
import com.born2go.signal.jobmanager.requirements.SimpleRequirement;
import com.born2go.signal.service.KeyCachingService;

public class MasterSecretRequirement extends SimpleRequirement implements ContextDependent {

  private transient Context context;

  public MasterSecretRequirement(Context context) {
    this.context = context;
  }

  @Override
  public boolean isPresent() {
    return KeyCachingService.getMasterSecret(context) != null;
  }

  @Override
  public void setContext(Context context) {
    this.context = context;
  }
}
