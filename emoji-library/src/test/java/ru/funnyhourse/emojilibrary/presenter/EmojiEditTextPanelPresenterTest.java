package ru.funnyhourse.emojilibrary.presenter;

import android.text.Editable;
import android.view.MenuItem;

import androidx.fragment.app.FragmentManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import ru.funnyhourse.emojilibrary.R;
import ru.funnyhourse.emojilibrary.view.EmojiEditTextPanelEventListener;
import ru.funnyhourse.emojilibrary.view.IEmojiEditTextPanel;

import static org.assertj.core.api.Assertions.assertThat;

public class EmojiEditTextPanelPresenterTest {
    @Mock
    EmojiEditTextPanelEventListener editTextPanelListener;

    @Mock
    IEmojiEditTextPanel editTextPanel;

    @Mock
    FragmentManager fm;

    @Mock
    MenuItem menuItem;

    @Mock
    Editable editable;

    @Mock
    Editable emptyEditable;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(editable.toString()).thenReturn("test");
        when(emptyEditable.toString()).thenReturn("");
    }

    @Test
    public void onBottomPanelMenuItemClick_onSendClickedWhenNoListener() {
        EmojiEditTextPanelPresenter presenter =
                EmojiEditTextPanelPresenter.newInstance(editTextPanel, fm);

        when(menuItem.getItemId()).thenReturn(R.id.action_mic);

        assertThat(presenter.onBottomPanelMenuItemClick(menuItem)).isEqualTo(false);
        verifyNoMoreInteractions(editTextPanelListener);
    }

    @Test
    public void onBottomPanelMenuItemClick_onSendClickedWhenHasListener() {
        EmojiEditTextPanelPresenter presenter =
                EmojiEditTextPanelPresenter.newInstance(editTextPanel, fm);

        when(menuItem.getItemId()).thenReturn(R.id.action_mic);
        when(editTextPanel.getInputText()).thenReturn(editable);

        presenter.setEventListener(editTextPanelListener);

        assertThat(presenter.onBottomPanelMenuItemClick(menuItem)).isEqualTo(true);
        verify(editTextPanelListener).onSendClicked();
        verifyNoMoreInteractions(editTextPanelListener);
    }

    @Test
    public void onBottomPanelMenuItemClick_onSendClickedWhenHasListenerWhenEmptyText() {
        EmojiEditTextPanelPresenter presenter =
                EmojiEditTextPanelPresenter.newInstance(editTextPanel, fm);

        when(menuItem.getItemId()).thenReturn(R.id.action_mic);
        when(editTextPanel.getInputText()).thenReturn(emptyEditable);

        presenter.setEventListener(editTextPanelListener);

        assertThat(presenter.onBottomPanelMenuItemClick(menuItem)).isEqualTo(true);
        verify(editTextPanelListener).onMicClicked();
        verifyNoMoreInteractions(editTextPanelListener);
    }

    @Test
    public void onBottomPanelMenuItemClick_onAttachClickedWhenNoListener() {
        EmojiEditTextPanelPresenter presenter =
                EmojiEditTextPanelPresenter.newInstance(editTextPanel, fm);

        when(menuItem.getItemId()).thenReturn(R.id.action_attach);

        assertThat(presenter.onBottomPanelMenuItemClick(menuItem)).isEqualTo(false);
        verifyNoMoreInteractions(editTextPanelListener);
    }

    @Test
    public void onBottomPanelMenuItemClick_onAttachClickedWhenHasListener() {
        EmojiEditTextPanelPresenter presenter =
                EmojiEditTextPanelPresenter.newInstance(editTextPanel, fm);

        when(menuItem.getItemId()).thenReturn(R.id.action_attach);
        when(editTextPanel.getInputText()).thenReturn(editable);

        presenter.setEventListener(editTextPanelListener);

        assertThat(presenter.onBottomPanelMenuItemClick(menuItem)).isEqualTo(true);
        verify(editTextPanelListener).onAttachClicked();
        verifyNoMoreInteractions(editTextPanelListener);
    }

    @Test
    public void onBottomPanelMenuItemClick_onAttachClickedWhenHasListenerWhenEmptyText() {
        EmojiEditTextPanelPresenter presenter =
                EmojiEditTextPanelPresenter.newInstance(editTextPanel, fm);

        when(menuItem.getItemId()).thenReturn(R.id.action_attach);
        when(editTextPanel.getInputText()).thenReturn(emptyEditable);

        presenter.setEventListener(editTextPanelListener);

        assertThat(presenter.onBottomPanelMenuItemClick(menuItem)).isEqualTo(true);
        verify(editTextPanelListener).onAttachClicked();
        verifyNoMoreInteractions(editTextPanelListener);
    }
}
