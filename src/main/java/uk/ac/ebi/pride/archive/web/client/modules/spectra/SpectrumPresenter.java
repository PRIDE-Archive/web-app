package uk.ac.ebi.pride.archive.web.client.modules.spectra;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.event.shared.EventBus;
import uk.ac.ebi.pride.archive.web.client.events.updates.VarianceUpdateEvent;
import uk.ac.ebi.pride.archive.web.client.modules.Presenter;
import uk.ac.ebi.pride.archive.web.client.modules.View;

/**
 * @author Jose A. Dianes <jdianes@ebi.ac.uk>
 *
 */
public class SpectrumPresenter extends Presenter<SpectrumPresenter.ThisView> implements VarianceUpdateEvent.Handler {

    public SpectrumPresenter(EventBus eventBus, ThisView view) {
        super(eventBus, view);

        // subscribe to events on PSMs selection changes, we need to react presenting the right spectra
        eventBus.addHandler(VarianceUpdateEvent.getType(), this);
    }

    public interface ThisView extends View {
        void showSpectra();
    }

    @Override
    public void onVarianceUpdateEvent(VarianceUpdateEvent event) {
        this.getView().showSpectra();
        SpeckTackleWrapper.create("/static/15814.json");
    }
}
