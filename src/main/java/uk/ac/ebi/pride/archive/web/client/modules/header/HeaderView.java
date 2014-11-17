package uk.ac.ebi.pride.archive.web.client.modules.header;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.*;
import uk.ac.ebi.pride.archive.web.client.utils.Pair;
import uk.ac.ebi.pride.archive.web.client.utils.factories.HyperlinkFactory;

import java.util.List;

/**
 * @author Pau Ruiz Safont <psafont@ebi.ac.uk>
 *         Date: 07/11/13
 *         Time: 12:08
 */
public class HeaderView implements HeaderPresenter.ThisView {

    @UiTemplate("HeaderView.ui.xml")
    interface HeaderUiBinder extends UiBinder<HTMLPanel, HeaderView> {
    }

    private static final String disclaimerLabel = "Inferred Sequence";
    private static final String disclaimerTooltip = "This protein sequence was not provided with the dataset, it has been inferred from the submitted protein accession. A peptide is matched using the reported coordinates, which may not match the peptide's position on the inferred sequence.";

    private static HeaderUiBinder ourUiBinder = GWT.create(HeaderUiBinder.class);

    @UiField
    VerticalPanel vPanel;

    @UiField
    Anchor assay;

    @UiField
    Label title;

    @UiField
    Label disclaimer;


    @UiField
    Label description;

    @UiField
    FlowPanel attributes;

    private HTMLPanel root;

    public HeaderView() {
        root = ourUiBinder.createAndBindUi(this);

        title.setText("");
        description.setText("");

    }

    @Override
    public void updateAssay(String assay) {
        this.assay.setHref("/pride/archive/assays/"+ assay);
        this.assay.setText(assay);
    }

    @Override
    public void updateTitle(String title) {
       this.title.setText(title);
    }

    @Override
    public void showSequenceDisclaimer(boolean show) {
        if (show) {
            this.disclaimer.setText(disclaimerLabel);
            this.disclaimer.setTitle(disclaimerTooltip);
        } else {
            this.disclaimer.setText("");
            this.disclaimer.setTitle("");
        }
    }

    @Override
    public void updateDescription(String description) {
        this.description.setText(description);
        this.description.setTitle("The protein description is compiled by PRIDE based on the provided protein identifier.");
    }

    @Override
    public void updateProperties(List<Pair<String, String>> links) {
        clearProperties();
        for(Pair<String, String> link : links) {
            attributes.add(HyperlinkFactory.getInlineHyperLink(link.getA(), link.getB()));
            if(link != links.get(links.size() - 1)) {
                attributes.add(new InlineLabel(", "));
            }
        }
    }

    @Override
    public void clearProperties() {
        attributes.clear();
    }

    @Override
    public void bindToContainer(AcceptsOneWidget container) {
        container.setWidget(root);
    }

    @Override
    public Widget asWidget() {
        return vPanel;
    }

    @Override
    public void setVisible(boolean visible) {
        asWidget().setVisible(visible);
    }
}
