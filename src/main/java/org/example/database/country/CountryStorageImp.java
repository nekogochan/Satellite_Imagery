package org.example.database.country;

import org.example.database.image.ImageStorage;
import org.example.database.region.RegionStorage;
import org.example.domain.Image;
import org.example.domain.location.Country;
import org.example.domain.location.LocationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CountryStorageImp implements CountryStorage {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ApplicationContext context;

    @Autowired
    private RegionStorage regionStorage;
    @Autowired
    private ImageStorage imageStorage;

    @Override
    public Country get(int id) {
        Country country = null;

        String sqlQuery = "SELECT * FROM country WHERE id = ?";
        country = jdbcTemplate.query(sqlQuery, context.getBean(CountryMapper.class), id).get(0);

        return country;
    }

    @Override
    public Country get(String name) {
        Country country = null;

        String sqlQuery = "SELECT * FROM country WHERE name = ?";
        country = jdbcTemplate.query(sqlQuery, context.getBean(CountryMapper.class), name).get(0);

        return country;
    }

    @Override
    public List<Country> getList() {
        String sqlQuery = "SELECT * FROM country";
        return jdbcTemplate.query(sqlQuery, context.getBean(CountryMapper.class));
    }

    @Override
    public void add(Country country) {
        String sqlQuery = "INSERT INTO country (name, description) VALUES (?, ?)";
        jdbcTemplate.update(sqlQuery, country.getName(), country.getDescription());
    }

    @Override
    public void update(Country country) {
        String sqlQuery = "UPDATE country SET name = ?, description = ? WHERE id = ?";
        jdbcTemplate.update(sqlQuery, country.getName(), country.getDescription(), country.getId());
    }

    @Override
    public void delete(int id) {

        regionStorage.deleteByParentId(id);

        Image image = new Image();
        image.setLocationType(LocationType.COUNTRY);
        image.setLocationId(id);
        imageStorage.delete(image);


        String sqlQuery = "DELETE FROM country WHERE id = ?";
        jdbcTemplate.update(sqlQuery, id);
    }
}
