package com.svalero.asociation.repository;

import com.svalero.asociation.model.InscripcionActividad;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InscripcionActividadRepository extends CrudRepository<InscripcionActividad, Long> {
    boolean existsByActividadIdAndParticipanteId(long actividadId, long participanteId);
    List<InscripcionActividad> findByActividadId(long actividadId);
    void deleteByActividadIdAndParticipanteId(long actividadId, long participanteId);

}
