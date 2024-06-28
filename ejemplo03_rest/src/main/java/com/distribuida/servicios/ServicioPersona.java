package com.distribuida.servicios;

import com.distribuida.db.Persona;

import java.util.List;

public interface ServicioPersona {

    Persona findById(Integer id);

    List<Persona> findAll();

    //    Ingresar Persona
    void insert(Persona persona);

    //    Actualizar Persona
    void update(Persona persona);

    //    Eliminar Persona
    String  deleteById(Integer id);

}
