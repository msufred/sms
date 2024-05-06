package org.gemseeker.sms.data.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ProductController implements ModelController<Product> {

    private final Database database;

    public ProductController(Database database) {
        this.database = database;
    }

    @Override
    public boolean insert(Product model) throws SQLException {
        String sql = String.format("INSERT INTO products (name, price, stock, serial, tag, date_created, date_updated) " +
                "VALUES ('%s', '%f', '%d', '%s', '%s', '%s', '%s')", model.getName(), model.getPrice(), model.getStock(),
                model.getSerial(), model.getTag(), model.getDateCreated(), model.getDateUpdated());
        return database.executeQuery(sql);
    }

    @Override
    public boolean update(Product model) throws SQLException {
        String sql = String.format("UPDATE products SET name='%s', price='%f', stock='%d', serial='%s', tag='%s', " +
                "date_updated='%s' WHERE id='%d'", model.getName(), model.getPrice(), model.getStock(), model.getSerial(),
                model.getTag(), LocalDateTime.now(), model.getId());
        return database.executeQuery(sql);
    }

    @Override
    public boolean update(int id, String col, String value) throws SQLException {
        String sql = String.format("UPDATE products SET %s='%s', date_updated='%s' WHERE id='%d'",
                col, value, LocalDateTime.now(), id);
        return database.executeQuery(sql);
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return update(id, "date_deleted", LocalDateTime.now().toString());
    }

    @Override
    public Product get(int id) throws SQLException {
        String sql = String.format("SELECT * FROM products WHERE id='%d' AND date_deleted IS NULL LIMIT 1", id);
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            if (rs.next()) return fetchInfo(rs);
        }
        return null;
    }

    @Override
    public ObservableList<Product> getAll() throws SQLException {
        String sql = "SELECT * FROM products WHERE date_deleted IS NULL";
        ObservableList<Product> list = FXCollections.observableArrayList();
        try (ResultSet rs = database.executeQueryWithResult(sql)) {
            while (rs.next()) list.add(fetchInfo(rs));
        }
        return list;
    }

    private Product fetchInfo(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt(1));
        product.setName(rs.getString(2));
        product.setPrice(rs.getDouble(3));
        product.setStock(rs.getInt(4));
        product.setSerial(rs.getString(5));
        product.setTag(rs.getString(6));
        product.setDateCreated(rs.getTimestamp(7).toLocalDateTime());
        product.setDateUpdated(rs.getTimestamp(8).toLocalDateTime());
        Timestamp dateDeleted = rs.getTimestamp(9);
        if (dateDeleted != null) product.setDateDeleted(dateDeleted.toLocalDateTime());
        return product;
    }
}
