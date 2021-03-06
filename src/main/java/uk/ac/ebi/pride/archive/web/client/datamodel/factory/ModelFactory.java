package uk.ac.ebi.pride.archive.web.client.datamodel.factory;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import uk.ac.ebi.pride.archive.web.client.exceptions.InvalidJSONException;

import java.util.logging.Logger;

/**
 * @author Pau Ruiz Safont <psafont@ebi.ac.uk>
 *         Date: 25/10/13
 *         Time: 15:44
 */
public abstract class ModelFactory {
    interface MyFactory extends AutoBeanFactory {
        AutoBean<Group> group();
        AutoBean<Protein> protein();
        AutoBean<PeptideMatch> peptideMatch();
        AutoBean<PeptideList> peptideList();
        AutoBean<Peptide> peptide();
        AutoBean<Spectrum> spectrum();
        AutoBean<ModifiedLocation> modifiedLocation();
    }

    private static Logger logger = Logger.getLogger(ModelFactory.class.getName());

    public static<T> T getModelObject(Class<T> tClass, String json) throws
            InvalidJSONException {
        MyFactory factory = GWT.create(MyFactory.class);
        try {
            logger.info("JSON: " + json);
            AutoBean<T> bean = AutoBeanCodex.decode(factory, tClass, json);
            return bean.as();
        }
        catch (Throwable e){
            throw new InvalidJSONException("Error parsing json object for " +
                                            tClass + ": " + json, e);
        }
    }
}
