package com.maxrocky.gundam.hibernate;

import java.util.List;

/**
 * Created by Tom on 2016/5/9 14:49.
 * Describe:
 */
public interface BaseRepository<PO> {

    void flush();

    void clear();

    PO get(String id);

    List<PO> getAll();

    void save(PO entity);

    void update(PO entity);

    void saveOrUpdate(PO entity);

    void deletePhysical(PO obj);

}
