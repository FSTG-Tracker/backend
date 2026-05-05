package com.universite.absences.service.interfaces;

import com.universite.absences.dto.request.AuthRequest;
import com.universite.absences.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse login(AuthRequest request);
}
