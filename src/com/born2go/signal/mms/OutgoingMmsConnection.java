package com.born2go.signal.mms;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.born2go.signal.transport.UndeliverableMessageException;
import com.google.android.mms.pdu_alt.SendConf;


public interface OutgoingMmsConnection {
  @Nullable
  SendConf send(@NonNull byte[] pduBytes, int subscriptionId) throws UndeliverableMessageException;
}
