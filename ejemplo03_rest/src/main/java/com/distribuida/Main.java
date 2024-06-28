package com.distribuida;

import com.distribuida.db.Persona;
import com.distribuida.servicios.ServicioPersona;
import com.distribuida.servicios.ServicioPersonaImpl;
import com.google.gson.Gson;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import spark.Request;
import spark.Response;

import java.util.List;

import static spark.Spark.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    static SeContainer container;
    static List<Persona> listarPersonas(Request req, Response res){

        res.type("application/json");

        var servicio = container.select(ServicioPersona.class).get();

        return servicio.findAll();
    }

    static Persona buscarPersona(Request req, Response res){

        res.type("application/json");
        String _id = req.params(":id");

        var servicio = container.select(ServicioPersona.class).get();

        var persona = servicio.findById(Integer.valueOf(_id));

        if(persona==null){
            halt(404,"Persona no encontrada");
        }

        return persona;
    }

    static Persona insertarPersona(Request req, Response res) {
        res.type("application/json");
        var servicio = container.select(ServicioPersona.class).get();
        Persona persona = new Gson().fromJson(req.body(), Persona.class);
        servicio.insert(persona);
        res.status(201);
        return persona;
    }

    static Persona actualizarPersona(Request req, Response res) {
        res.type("application/json");
        String _id = req.params(":id");
        var servicio = container.select(ServicioPersona.class).get();
        Persona persona = new Gson().fromJson(req.body(), Persona.class);
        persona.setId(Integer.valueOf(_id));
        servicio.update(persona);
        return persona;
    }

    static String eliminarPersona(Request req, Response res) {
        res.type("application/json");
        String _id = req.params(":id");
        var servicio = container.select(ServicioPersona.class).get();
        servicio.deleteById(Integer.valueOf(_id));
        res.status(204);
        return "";
    }

    public static void main(String[] args) {
         container= SeContainerInitializer
                .newInstance()
                .initialize();

        ServicioPersona servicio = container
                .select(ServicioPersona.class)
                .get();

        servicio
                .findAll()
                .stream()
                .map(Persona::getNombre)
                .forEach(System.out::println);

        port(8080);
        Gson gson = new Gson();

        get("/personas", Main::listarPersonas,gson::toJson);
        get("/personas/:id", Main::buscarPersona, gson::toJson);
        post("/personas", Main::insertarPersona, gson::toJson);
        put("/personas/:id", Main::actualizarPersona, gson::toJson);
        delete("/personas/:id", Main::eliminarPersona, gson::toJson);
    }
}
