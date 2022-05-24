package io.recruitment.assessment.api.repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import io.recruitment.assessment.api.model.Order;







public class OrderMapper implements RowMapper<Order> {

   @Override   
   public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
      Order order = new Order();
      order.setOrderID(rs.getInt("orderID"));
      order.setUserID(rs.getInt("userID"));
      order.setProductID(rs.getInt("productID"));
      return order;
   }

  
}
