package com.distribuida.servicios;

import com.distribuida.db.Persona;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

@ApplicationScoped
public class ServicioPersonaImpl implements ServicioPersona {
    @Inject
    EntityManager em;

    @Override
    public Persona findById(Integer id) {
        return em.find(Persona.class, id);
    }

    @Override
    public List<Persona> findAll() {
        return em
                .createQuery("select o from Persona o order by id asc", Persona.class).getResultList();
    }

    @Override
    public void insert(Persona persona) {
        em.getTransaction().begin();
        em.persist(persona);
        em.getTransaction().commit();
    }

    @Override
    public void update(Persona persona) {
        em.getTransaction().begin();
        em.merge(persona);
        em.getTransaction().commit();
    }

    @Override
    public String deleteById(Integer id) {
        Persona persona = findById(id);

        if (persona != null) {
            em.getTransaction().begin();
            em.remove(persona);
            em.getTransaction().commit();
            return "Persona eliminada exitosamente.";
        } else {
            return "La persona con ID " + id + " no fue encontrada.";
        }

    }

}
