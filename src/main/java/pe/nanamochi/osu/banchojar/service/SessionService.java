package pe.nanamochi.osu.banchojar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.nanamochi.osu.banchojar.entities.db.Session;
import pe.nanamochi.osu.banchojar.repository.SessionRepository;

import java.util.UUID;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    public Session getSessionByID(UUID id) {
        return sessionRepository.findById(id).orElse(null);
    }

    public Session saveSession(Session session) {
        return sessionRepository.save(session);
    }
}
