package com.born2go.signal.jobmanager.dependencies;

import android.content.Context;

import com.born2go.signal.jobmanager.Job;
import com.born2go.signal.jobmanager.requirements.Requirement;

public class AggregateDependencyInjector {

  private final DependencyInjector dependencyInjector;

  public AggregateDependencyInjector(DependencyInjector dependencyInjector) {
    this.dependencyInjector = dependencyInjector;
  }

  public void injectDependencies(Context context, Job job) {
    if (job instanceof ContextDependent) {
      ((ContextDependent)job).setContext(context);
    }

    for (Requirement requirement : job.getRequirements()) {
      if (requirement instanceof ContextDependent) {
        ((ContextDependent)requirement).setContext(context);
      }
    }

    if (dependencyInjector != null) {
      dependencyInjector.injectDependencies(job);

      for (Requirement requirement : job.getRequirements()) {
        dependencyInjector.injectDependencies(requirement);
      }
    }
  }

}
