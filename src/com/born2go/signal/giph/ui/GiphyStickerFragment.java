package com.born2go.signal.giph.ui;


import android.os.Bundle;
import android.support.v4.content.Loader;

import com.born2go.signal.giph.model.GiphyImage;
import com.born2go.signal.giph.net.GiphyStickerLoader;

import java.util.List;

public class GiphyStickerFragment extends GiphyFragment {
  @Override
  public Loader<List<GiphyImage>> onCreateLoader(int id, Bundle args) {
    return new GiphyStickerLoader(getActivity(), searchString);
  }
}
