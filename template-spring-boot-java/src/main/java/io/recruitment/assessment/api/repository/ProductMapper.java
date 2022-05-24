package io.recruitment.assessment.api.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import io.recruitment.assessment.api.model.Product;


public class ProductMapper implements RowMapper<Product> {

   @Override   //For mapping the rows returned for the product from db
   public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
      Product product = new Product();
      product.setProductID(rs.getInt("ProductID"));
      product.setProductName(rs.getString("Name"));
      product.setProductDesc(rs.getString("Description"));
      product.setProductPrice(rs.getInt("Price"));
      product.setProductDiscount(rs.getInt("Discount"));
      return product;
   }

}