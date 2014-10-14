package uk.ac.ebi.pride.archive.web.client.modules.spectra;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import uk.ac.ebi.pride.archive.web.client.datamodel.adapters.ProteinAdapter;
import uk.ac.ebi.pride.archive.web.client.datamodel.factory.Spectrum;
import uk.ac.ebi.pride.archive.web.client.events.updates.VarianceUpdateEvent;
import uk.ac.ebi.pride.archive.web.client.modules.ViewWithUiHandlers;
import uk.ac.ebi.pride.archive.web.client.modules.sequence.SequenceUiHandler;
import uk.ac.ebi.pride.archive.web.client.modules.variances.VariancesPresenter;
import uk.ac.ebi.pride.archive.web.client.utils.factories.ModuleContainerFactory;
import uk.ac.ebi.pride.widgets.client.disclosure.client.ModuleContainer;
import uk.ac.ebi.pride.widgets.client.sequence.client.SequenceViewer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jose A. Dianes <jdianes@ebi.ac.uk>
 *
 */
public class SpectrumView extends ViewWithUiHandlers<SequenceUiHandler> implements SpectrumPresenter.ThisView {

    private HTMLPanel panel;
    private ModuleContainer outerBox;
    private Map<VariancesPresenter, SpeckTackleWrapper> viewersCache; // TODO

    public SpectrumView() {
        viewersCache = new HashMap<>();
        outerBox = ModuleContainerFactory.getModuleContainer("Peak " +
                "List");
    }



    @Override
    public void bindToContainer(AcceptsOneWidget container) {
        container.setWidget(outerBox);
    }

    @Override
    public void setVisible(boolean visible) {
        asWidget().setVisible(visible);
    }



    @Override
    public Widget asWidget() {
        return outerBox;
    }


    @Override
    public void showSpectrum(Spectrum spectrum) {
        outerBox.setContent(null);
        panel = new HTMLPanel("<div id='stgraph' class='stgraph'></div>");
        outerBox.setContent(panel);
        outerBox.setOpen(true);
        String spectrumJson = "pride/archive/viewer/service/spectrum/" + spectrum.getId();
        SpeckTackleWrapper.create(spectrumJson);
    }
}
