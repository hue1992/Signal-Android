package com.born2go.signal.database.loaders;

import android.content.Context;
import android.database.Cursor;

import com.born2go.signal.database.DatabaseFactory;
import com.born2go.signal.util.AbstractCursorLoader;

public class BlockedContactsLoader extends AbstractCursorLoader {

  public BlockedContactsLoader(Context context) {
    super(context);
  }

  @Override
  public Cursor getCursor() {
    return DatabaseFactory.getRecipientDatabase(getContext())
                          .getBlocked();
  }

}
