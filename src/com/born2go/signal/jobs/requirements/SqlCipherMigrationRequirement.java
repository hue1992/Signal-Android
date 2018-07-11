package com.born2go.signal.jobs.requirements;


import android.content.Context;
import android.support.annotation.NonNull;

import com.born2go.signal.jobmanager.dependencies.ContextDependent;
import com.born2go.signal.jobmanager.requirements.SimpleRequirement;
import com.born2go.signal.util.TextSecurePreferences;

public class SqlCipherMigrationRequirement extends SimpleRequirement implements ContextDependent {

  @SuppressWarnings("unused")
  private static final String TAG = SqlCipherMigrationRequirement.class.getSimpleName();

  private transient Context context;

  public SqlCipherMigrationRequirement(@NonNull Context context) {
    this.context = context;
  }

  @Override
  public void setContext(Context context) {
    this.context = context;
  }

  @Override
  public boolean isPresent() {
    return !TextSecurePreferences.getNeedsSqlCipherMigration(context);
  }
}
