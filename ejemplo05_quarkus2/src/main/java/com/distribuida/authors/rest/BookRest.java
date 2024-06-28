package com.distribuida.authors.rest;

import com.distribuida.authors.db.Book;
import com.distribuida.authors.repo.BookRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
@ApplicationScoped //componente CDI para inyeccion de dependencias
public class BookRest {

    @Inject
    BookRepository repo;

    @GET
    public List<Book> findAll() {
        return repo.listAll();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Integer id) {
        var op =  repo.findByIdOptional(id);
        if(op.isEmpty()){
            //no encontrado
            return Response.status(Response.Status.NOT_FOUND).build();

        }

        return Response.ok(op.get()).build();
    }

    @POST
    public Response create(Book book) {

        repo.persist(book);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Integer id, Book book) {
        var obj = repo.findById(id);
        obj.setIsbn(book.getIsbn());
        obj.setTitle(book.getTitle());
        obj.setPrice(obj.getPrice());
        obj.setAuthorId(book.getAuthorId());
        //aqui ya no hace falta actualziar porque como ya esta dentro de una transaccion
        //este ya se modifica automaticamente
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id) {
        repo.deleteById(id);
        return Response.ok().build();
    }
}
