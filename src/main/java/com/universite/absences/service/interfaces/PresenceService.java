package com.universite.absences.service.interfaces;

import com.universite.absences.dto.request.PresenceIARequest;
import com.universite.absences.dto.response.PresenceIAResponse;

public interface PresenceService {
    PresenceIAResponse enregistrerPresenceIA(PresenceIARequest request);
}
