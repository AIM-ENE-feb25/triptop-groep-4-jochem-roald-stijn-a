package com.triptop.externe_service_prototype.api;

import java.util.Optional;

public interface ExternalAPIHandler {
    Optional<Response> sendRequest(Request request, boolean dataMayGetOutdated);
}
