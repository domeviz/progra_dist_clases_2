package com.distribuida.servicios;

import com.distribuida.db.Persona;

import java.util.List;

public interface ServicioPersona {

    List<Persona> findAll();

    Persona findById(int id);

    void insert(Persona persona);

    void update(Persona persona);

    void delete(Integer id);
}


