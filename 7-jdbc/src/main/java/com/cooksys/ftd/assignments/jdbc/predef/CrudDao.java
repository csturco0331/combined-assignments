package com.cooksys.ftd.assignments.jdbc.predef;

public interface CrudDao<E extends Entity<I>, I> {

    I create(E entity);

    E read(I id);

    void update(E entity);

    void delete(E entity);

}
