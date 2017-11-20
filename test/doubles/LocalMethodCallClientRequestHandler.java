package doubles;

import frs.broker.ClientRequestHandler;
import frs.broker.Invoker;
import frs.broker.ReplyObject;
import frs.broker.RequestObject;

public class LocalMethodCallClientRequestHandler implements ClientRequestHandler {

    Invoker invoker;
    RequestObject lastRequest;
    ReplyObject lastReply;

    public LocalMethodCallClientRequestHandler(Invoker invoker){
        this.invoker = invoker;
    }

    @Override
    public ReplyObject sendToServer(RequestObject requestObject){
        lastRequest = requestObject;
        lastReply = invoker.handleRequest(requestObject.getObjectId(), requestObject.getOperationName(), requestObject.getPayload());
        return lastReply;
    }

    public ReplyObject getLastReply() {
        return lastReply;
    }

    public RequestObject getLastRequest() {
        return lastRequest;
    }
}
