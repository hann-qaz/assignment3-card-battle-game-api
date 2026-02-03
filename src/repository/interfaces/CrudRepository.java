package repository.interfaces;

import exception.DatabaseException;
import exception.ResourceNotFoundException;
import java.util.List;

/*
  Generic CRUD Repository Interface
  Demonstrates: Generics, Interface Segregation Principle, Dependency Inversion Principle
  @param <T> The entity type
 */
public interface CrudRepository<T> {
    void create(T entity) throws DatabaseException;
    List<T> getAll() throws DatabaseException;
    T getById(int id) throws ResourceNotFoundException, DatabaseException;
    void update(int id, T entity) throws DatabaseException, ResourceNotFoundException;
    void delete(int id) throws ResourceNotFoundException, DatabaseException;

    /*
      Default method - demonstrates Interface default methods
      Counts all entities in the repository
     */
    default int count() throws DatabaseException {
        return getAll().size();
    }

    /*
      Static method - demonstrates Interface static methods
      Validates that an ID is positive
     */
    static boolean isValidId(int id) {
        return id > 0;
    }
}