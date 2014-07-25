package uk.ac.ebi.pride.archive.web.client.modules.data.retrievers;

import com.google.gwt.http.client.*;
import uk.ac.ebi.pride.archive.web.client.modules.data.Transaction;
import uk.ac.ebi.pride.archive.web.client.modules.data.TransactionHandler;
import uk.ac.ebi.pride.archive.web.client.modules.data.UnacceptableResponse;
import uk.ac.ebi.pride.archive.web.client.utils.Console;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Pau Ruiz Safont <psafont@ebi.ac.uk>
 *         Date: 08/01/14
 *         Time: 16:14
 */
class DataRequester implements RequestCallback {

    private final static int UNPROCESSABLE_ENTITY = 422;
    private final String id;
    private final Class responseType;
    private Collection<TransactionHandler> handlers = new ArrayList<>();

    public DataRequester(String identifier, String url, Class type, Collection<TransactionHandler> responseHandlers) {
        id = identifier;
        responseType = type;
        handlers = responseHandlers;

        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
        builder.setHeader("Accept", "application/json");

        try {
            builder.sendRequest(null, this);
        } catch(RequestException e) {
            onInvalidResponse(new UnacceptableResponse(0, "", e.getMessage(), responseType, id));
        }
    }

    @Override
    public void onResponseReceived(Request request, Response response) {
        if(response == null) {
            if (Console.VERBOSE) {
                Console.info("Error: server response is null!");
            }
            onInvalidResponse(new UnacceptableResponse(0, "", "No response from the server.", responseType, id));
        } else if(response.getStatusCode() == Response.SC_OK) {
            if (Console.VERBOSE) {
                Console.info("Valid response from server.");
            }
            onValidResponse(response);
        } else if (response.getStatusCode() == Response.SC_UNAUTHORIZED || response.getStatusCode() == Response.SC_FORBIDDEN) {
            if (Console.VERBOSE) {
                Console.info("ERROR: " + response.getStatusCode() + " Illegal access!");
            }
            onInvalidResponse(new UnacceptableResponse(response.getStatusCode(),
                                                       response.getStatusText(),
                                                       "Illegal access!",
                                                       responseType,
                                                       id));
        } else if (response.getStatusCode() == UNPROCESSABLE_ENTITY || response.getStatusCode() == Response.SC_NOT_FOUND) {
            if (Console.VERBOSE) {
                Console.info("ERROR: " + response.getStatusCode() + " No valid data found!");
            }
            onInvalidResponse(new UnacceptableResponse(response.getStatusCode(),
                    response.getStatusText(),
                    "No valid data found!",
                    responseType,
                    id));
        } else {
            if (Console.VERBOSE) {
                Console.info("Error: Invalid response from server. status:" + response.getStatusCode() + "/" +response.getStatusText() );
            }
            onInvalidResponse(new UnacceptableResponse(response.getStatusCode(),
                                                       response.getStatusText(),
                                                       "The Server couldn't fulfill the request.",
                                                       responseType,
                                                       id));
        }
    }

    @Override
    public void onError(Request request, Throwable exception) {
        onInvalidResponse(new UnacceptableResponse(0, "", exception.getMessage(), responseType, id));
    }

    private void onValidResponse(Response response) {
        onTransactionDone(new Transaction(response, id, responseType));
    }

    private void onInvalidResponse(UnacceptableResponse r) {
        onTransactionDone(new Transaction(r, id, responseType));
    }

    private void onTransactionDone(Transaction transaction) {
        for(TransactionHandler handler : handlers) {
            handler.onTransactionFinished(transaction);
        }
    }
}
