package com.born2go.signal.jobs;

import android.content.Context;

import com.born2go.signal.jobmanager.Job;
import com.born2go.signal.jobmanager.JobParameters;
import com.born2go.signal.jobmanager.dependencies.ContextDependent;

public abstract class ContextJob extends Job implements ContextDependent {

  protected transient Context context;

  protected ContextJob(Context context, JobParameters parameters) {
    super(parameters);
    this.context = context;
  }

  public void setContext(Context context) {
    this.context = context;
  }

  protected Context getContext() {
    return context;
  }
}
