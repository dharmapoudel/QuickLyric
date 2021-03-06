/*
 * *
 *  * This file is part of QuickLyric
 *  * Created by geecko
 *  *
 *  * QuickLyric is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * QuickLyric is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  * You should have received a copy of the GNU General Public License
 *  * along with QuickLyric.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.geecko.QuickLyric.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.geecko.QuickLyric.R;
import com.geecko.QuickLyric.adapter.SearchSuggestionAdapter;
import com.geecko.QuickLyric.utils.LyricsSearchSuggestionsProvider;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class MaterialSuggestionsSearchView extends MaterialSearchView {

    private String[] mSuggestions;
    private Drawable suggestionIcon;
    private Drawable closeIcon;

    public MaterialSuggestionsSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MaterialSuggestionsSearchView(Context context) {
        super(context);
        init();
    }

    @SuppressWarnings("deprecation")
    private void init() {
        setSubmitOnClick(true);
        if (LyricsSearchSuggestionsProvider.database == null)
            LyricsSearchSuggestionsProvider.setDatabase(new LyricsSearchSuggestionsProvider(getContext())
                    .getWritableDatabase());
        Resources.Theme theme = getContext().getTheme();
        TypedValue hintColor = new TypedValue();
        TypedValue suggestionColor = new TypedValue();
        TypedValue themeName = new TypedValue();
        theme.resolveAttribute(android.R.attr.textColorSecondary, hintColor, true);
        theme.resolveAttribute(android.R.attr.textColorSecondaryInverse, suggestionColor, true);
        theme.resolveAttribute(R.attr.themeName, themeName, true);
        setHintTextColor(hintColor.data);
        ((ImageView) findViewById(com.miguelcatalan.materialsearchview.R.id.action_up_btn))
                .setColorFilter(suggestionColor.data, PorterDuff.Mode.SRC_IN);
        if (suggestionIcon != null) {
            suggestionIcon.setColorFilter(suggestionColor.data, PorterDuff.Mode.SRC_IN);
            setSuggestionIcon(suggestionIcon);
        }
        if (closeIcon != null) {
            closeIcon.setColorFilter(hintColor.data, PorterDuff.Mode.SRC_IN);
            setCloseIcon(closeIcon);
        }
    }

    @Override
    public void setSuggestionIcon(Drawable d) {
        this.suggestionIcon = d;
        super.setSuggestionIcon(d);
    }

    @Override
    public void setCloseIcon(Drawable d) {
        this.closeIcon = d;
        super.setCloseIcon(d);
    }

    public Drawable getCloseIcon() {
        return closeIcon;
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(new SearchSuggestionAdapter(getContext(), mSuggestions));
    }

    @Override
    public void setSuggestions(String[] suggestions) {
        this.mSuggestions = suggestions;
        super.setSuggestions(suggestions);
    }

    public boolean hasSuggestions() {
        return mSuggestions != null;
    }

    public void refreshSuggestions() {
        Editable text = ((EditText) ((Activity) getContext()).findViewById(R.id.searchTextView)).getText();
        setQuery(text, false);
    }

    public String[] getHistory() {
        return LyricsSearchSuggestionsProvider.getHistory(getContext());
    }
/*
    @Override
    public void onDetachedFromWindow() {
        if (LyricsSearchSuggestionsProvider.database != null
                && LyricsSearchSuggestionsProvider.database.isOpen())
            LyricsSearchSuggestionsProvider.database.close();
        super.onDetachedFromWindow();
    } */
}
