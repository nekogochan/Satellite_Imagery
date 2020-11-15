package org.example.database.image;

import org.example.domain.Image;
import org.example.domain.location.LocationType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImageMapper implements RowMapper<Image> {
    @Override
    public Image mapRow(ResultSet rs, int i) throws SQLException {
        Image image = new Image();

        image.setLocationId(rs.getInt("location_id"));

        LocationType locationType = LocationType.fromString(rs.getString("location_type"));
        image.setLocationType(locationType);

        Blob blob = rs.getBlob("data");
        int length = (int) blob.length();
        byte[] data = blob.getBytes(1, length);
        image.setData(data);

        return image;
    }
}
