package com.distribuida;

import com.distribuida.config.Jpaconfig;
import com.distribuida.db.Persona;
import com.distribuida.servicios.ServicioPersona;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spark.Request;
import spark.Response;

import java.util.List;

import static spark.Spark.*;

public class Main {

    static ServicioPersona servicio;

    static AnnotationConfigApplicationContext context;

    static List<Persona> listarPersonas(Request req, Response res) {

        res.type("application/json");

        servicio = context.getBean(ServicioPersona.class);

        return servicio.findAll();
    }

    static Persona buscarPersona(Request req, Response res) {

        res.type("application/json");
        String _id = req.params(":id");

        servicio = context.getBean(ServicioPersona.class);

        var persona = servicio.findById(Integer.valueOf(_id));

        if (persona == null) {
            halt(404, "Persona no encontrada");
        }

        return persona;
    }

    static String insertarPersona(Request req, Response res) {

        servicio = context.getBean(ServicioPersona.class);

        String body=req.body();

        JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
        String nombre = jsonObject.get("nombre").getAsString();
        String direccion = jsonObject.get("direccion").getAsString();
        Integer edad = jsonObject.get("edad").getAsInt();
        Integer id = jsonObject.get("id").getAsInt();


        Persona persona = new Persona();
        persona.setId(id);
        persona.setNombre(nombre);
        persona.setDireccion(direccion);
        persona.setEdad(edad);

        servicio.insert(persona);

        return "Persona Insertada!";
    }

    static String actualizarPersona(Request req, Response res) {

        servicio = context.getBean(ServicioPersona.class);

        String _id = req.params(":id");

        String body=req.body();

        JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
        String nombre = jsonObject.get("nombre").getAsString();
        String direccion = jsonObject.get("direccion").getAsString();
        Integer edad = jsonObject.get("edad").getAsInt();

        var persona = new Persona();
        persona.setId(Integer.valueOf(_id));
        persona.setNombre(nombre);
        persona.setDireccion(direccion);
        persona.setEdad(edad);

        try {
            servicio.update(persona);
        } catch (Exception e) {
            return "Persona no encontrada";
        }

        return "persona" + _id + " Actualizada!";
    }

    static String eliminarPersona(Request req, Response res) {

        servicio = context.getBean(ServicioPersona.class);

        String _id = req.params(":id");

        try {
            servicio.delete(Integer.valueOf(_id));
        } catch (Exception e) {
            e.printStackTrace();
            return "Persona no Encontrada";
        }


        return "persona" + _id + " Eliminada!";
    }

    public static void main(String[] args) {
        context = new AnnotationConfigApplicationContext(Jpaconfig.class);


        // Configura Spark Java
        port(8080);
        Gson gson = new Gson();

        get("/personas", Main::listarPersonas, gson::toJson);
        get("/personas/:id", Main::buscarPersona, gson::toJson);
        post("/personas", Main::insertarPersona);
        put("/personas/:id", Main::actualizarPersona);
        delete("/personas/:id", Main::eliminarPersona);

        // Cierra el contexto de Spring cuando la aplicación termine
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            context.close();
        }));
    }
}
