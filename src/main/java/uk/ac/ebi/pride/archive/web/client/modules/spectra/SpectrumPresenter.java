package uk.ac.ebi.pride.archive.web.client.modules.spectra;

import com.google.web.bindery.event.shared.EventBus;
import uk.ac.ebi.pride.archive.web.client.modules.Presenter;
import uk.ac.ebi.pride.archive.web.client.modules.View;

/**
 * @author Jose A. Dianes <jdianes@ebi.ac.uk>
 *
 */
public class SpectrumPresenter extends Presenter<SpectrumPresenter.ThisView> {

    protected SpectrumPresenter(EventBus eventBus, ThisView view) {
        super(eventBus, view);
    }

    public interface ThisView extends View {

    }

}
