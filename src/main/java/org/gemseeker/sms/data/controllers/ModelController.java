package org.gemseeker.sms.data.controllers;

import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface ModelController<T> {
    boolean insert(T model) throws SQLException;
    boolean update(T model) throws SQLException;
    boolean update(int id, String col, String value) throws SQLException;
    boolean delete(int id) throws SQLException;
    T get(int id) throws SQLException;
    ObservableList<T> getAll() throws SQLException;
}
