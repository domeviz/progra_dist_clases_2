package com.distribuida.servicios;

import com.distribuida.db.Persona;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioPersonaImpl implements ServicioPersona {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public List<Persona> findAll() {
        return em.createQuery("select p from Persona p order by id asc", Persona.class)
                .getResultList();
    }

    @Override
    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public Persona findById(int id) {
        return em.find(Persona.class, id);
    }

    @Override
    @Transactional
    public void insert(Persona persona) {
        try {
            em.persist(persona);
        } catch (Exception e) {
            System.out.println("No se pudo persistir el objeto");
            throw e;  // Asegúrate de que la transacción se gestione correctamente
        }
    }

    @Override
    @Transactional
    public void update(Persona persona) {
        try {
            em.merge(persona);
        } catch (Exception e) {
            System.out.println("No se pudo actualizar el objeto");
            throw e;  // Asegúrate de que la transacción se gestione correctamente
        }
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        try {
            Persona persona = this.findById(id);
            if (persona != null) {
                em.remove(persona);
            } else {
                System.out.println("Persona no encontrada");
            }
        } catch (Exception e) {
            System.out.println("No se pudo eliminar el objeto");
            throw e;  // Asegúrate de que la transacción se gestione correctamente
        }
    }
}
