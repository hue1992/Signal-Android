package com.born2go.signal.contacts.avatars;

import android.support.annotation.NonNull;

import com.born2go.signal.color.MaterialColor;
import com.born2go.signal.color.MaterialColors;

public class ContactColors {

  public static final MaterialColor UNKNOWN_COLOR = MaterialColor.GREY;

  public static MaterialColor generateFor(@NonNull String name) {
    return MaterialColors.CONVERSATION_PALETTE.get(Math.abs(name.hashCode()) % MaterialColors.CONVERSATION_PALETTE.size());
  }

}
