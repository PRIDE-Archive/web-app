package uk.ac.ebi.pride.archive.web.client.modules.spectra;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * @author Jose A. Dianes <jdianes@ebi.ac.uk>
 * @author Noemi del Toro <ntoro@ebi.ac.uk>
 *     Wrapper to acomodate the Lorikeet jquery plugin to GWT.
 */
public class LorikeetModsWrapper extends JavaScriptObject {

    protected LorikeetModsWrapper() {

    }

    public static native LorikeetModsWrapper create(int index, double modMass, char aminoAcid) /*-{
        return {index: index, modMass: modMass, aminoAcid: aminoAcid};
    }-*/;


    // Typically, methods on overlay types are JSNI
    public final native String getIndex() /*-{ return this.index; }-*/;
    public final native String getModMass()  /*-{ return this.modMass;  }-*/;
    public final native String getAminoAcid()  /*-{ return this.aminoAcid;  }-*/;


}
