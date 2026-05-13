import com.yourproject.model.OrderItem; // Giả sử OrderItem là Entity Panache
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Phụ trách nghiệp vụ thao tác với Chi tiết đơn hàng (Order_Items).
 */
@ApplicationScoped
public class OrderItemService {

    /**
     * Thực hiện lưu danh sách các chi tiết sản phẩm thuộc một đơn hàng.
     * Sử dụng @Transactional để đảm bảo tính toàn vẹn dữ liệu: 
     * nếu bất kỳ item nào lưu thất bại, toàn bộ giao dịch sẽ được rollback.
     */
    @Transactional
    public void createOrderItems(List<OrderItem> items) {
        for (OrderItem item : items) {
            // Panache ORM: persist() là phương thức được cung cấp để lưu Entity
            item.persist();
        }
    }

    // Thêm các phương thức khác như lấy chi tiết đơn hàng theo orderId (findById, listAll,...)
    public List<OrderItem> getItemsByOrderId(long orderId) {
        // Ví dụ dùng Panache list()
        return OrderItem.list("orderId", orderId).list();
    }
}