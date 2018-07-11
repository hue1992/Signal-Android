package com.born2go.signal;

import android.support.annotation.NonNull;

import com.born2go.signal.database.model.ThreadRecord;

import com.born2go.signal.mms.GlideRequests;

import java.util.Locale;
import java.util.Set;

public interface BindableConversationListItem extends Unbindable {

  public void bind(@NonNull ThreadRecord thread,
                   @NonNull GlideRequests glideRequests, @NonNull Locale locale,
                   @NonNull Set<Long> selectedThreads, boolean batchMode);
}
