package uk.ac.ebi.pride.archive.web.client.modules.spectra;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import uk.ac.ebi.pride.archive.web.client.datamodel.adapters.ProteinAdapter;
import uk.ac.ebi.pride.archive.web.client.datamodel.factory.Peptide;
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
 * Note: this viewer receives the whole JSON objectified to be shown. However, so far we haven found a way for the
 * SpeckTackle JS component to be provided with objects to display. Right now it just uses the JSON file as a resource
 * and therefore a URL is passed to the object.
 *
 */
public class SpectrumView extends ViewWithUiHandlers<SequenceUiHandler> implements SpectrumPresenter.ThisView {

    private HTMLPanel panel;
    private SimplePanel simplePanel;
    private ModuleContainer outerBox;
    private Map<VariancesPresenter, SpeckTackleWrapper> viewersCache; // TODO

    public SpectrumView() {
        viewersCache = new HashMap<>();
        outerBox = ModuleContainerFactory.getModuleContainer("Peak " +
                "List");

        //SpeckTackleWrapper.initChart();
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
    public void showSpectrum(Peptide variance) {
        simplePanel = new SimplePanel();
        simplePanel.getElement().setId(HTMLPanel.createUniqueId());
//        outerBox.setContent(null);
//        panel = new HTMLPanel("<div id='stgraph' class='stgraph'></div>");
        outerBox.setContent(simplePanel);
        outerBox.setOpen(true);
        outerBox.setHeight("500px");
//        String spectrumJson = "/pride/archive/viewer/service/spectrum/" + variance.getId();
        String spectrumJson = "/Users/jdianes/Documents/workspace/pride-archive-webapp/pride-archive-webapp/src/main/webapp/static/15814.json";
//        String annotationJson = "/pride/archive/viewer/service/spectrum/" + variance.getId() + "/annotations";
        String annotationJson = "/Users/jdianes/Documents/workspace/pride-archive-webapp/pride-archive-webapp/src/main/webapp/static/15814_anno.json";
        SpeckTackleWrapper.showSpectra(spectrumJson, annotationJson, simplePanel.getElement().getId());
    }
}
